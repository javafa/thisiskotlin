package net.flow9.googlemapsclustering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import net.flow9.googlemapsclustering.data.Library
import net.flow9.googlemapsclustering.data.Row
import net.flow9.googlemapsclustering.databinding.ActivityMapsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // 3. 클러스터 매니저를 정의한다.
    private lateinit var clusterManager: ClusterManager<Row>

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

        // 4. 클러스터 매니저 세팅
        clusterManager = ClusterManager(this, mMap)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

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

    fun showLibraries(libraries: Library) {
        val latLngBounds = LatLngBounds.Builder()

        for (lib in libraries.SeoulPublicLibraryInfo.row) {
            //5. 기존 마커 세팅코드는 삭제하고 클러스터 메니저에 데이터를 추가하는 코드만 넣어준다.
            clusterManager.addItem(lib)

            // 첫 화면에 보여줄 범위를 정하기 위해서 아래 코드 두 줄은 남겨둔다.
            val position = LatLng(lib.XCNTS.toDouble(), lib.YDNTS.toDouble())
            latLngBounds.include(position)
        }

        val bounds = latLngBounds.build()
        val padding = 0
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(updated)
    }
}
