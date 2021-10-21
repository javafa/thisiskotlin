package kr.co.hanbit.base

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.hanbit.base.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                Log.d("카메라","촬영 성공")
            } else {
                Log.d("카메라","촬영 실패")
            }
        }

        binding.btnCamera.setOnClickListener {
            requestPermissions(arrayOf(Manifest.permission.CAMERA))
        }
    }

    override fun permissionGranted() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResult.launch(intent)
    }

    override fun permissionDenied() {
        Toast.makeText(baseContext, "권한 거부됨", Toast.LENGTH_LONG).show()
    }
}