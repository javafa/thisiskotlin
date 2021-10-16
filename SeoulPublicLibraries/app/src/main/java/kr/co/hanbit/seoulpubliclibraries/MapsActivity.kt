package kr.co.hanbit.seoulpubliclibraries

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kr.co.hanbit.seoulpubliclibraries.data.Library
import kr.co.hanbit.seoulpubliclibraries.databinding.ActivityMapsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        loadLibraries()
    }

    fun loadLibraries() {
        val retrofit = Retrofit.Builder()
                .baseUrl(SeoulOpenApi.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val seoulOpenService = retrofit.create(SeoulOpenService::class.java)
        seoulOpenService
                .getLibrary(SeoulOpenApi.API_KEY)
                .enqueue(object : Callback<Library> {
                    override fun onFailure(call: Call<Library>, t: Throwable) {
                        Toast.makeText(baseContext
                                , "서버에서 데이터를 가져올 수 없습니다."
                                , Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<Library>, response: Response<Library>) {
                        showLibraries(response.body() as Library)
                    }
                })
    }

    fun showLibraries(libraries:Library) {
        val latLngBounds = LatLngBounds.Builder()
        for (lib in libraries.SeoulPublicLibraryInfo.row) {
            val position = LatLng(lib.XCNTS.toDouble(), lib.YDNTS.toDouble())
            val marker = MarkerOptions().position(position).title(lib.LBRRY_NAME)

            var obj = mMap.addMarker(marker)
            obj.tag = lib.HMPG_URL

            mMap.setOnMarkerClickListener {
                if (it.tag != null) {
                    var url = it.tag as String
                    if (!url.startsWith("http")) {
                        url = "http://${url}"
                    }
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                true
            }

            latLngBounds.include(marker.position)
        }
        val bounds = latLngBounds.build()
        val padding = 0
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(updated)
    }
}
