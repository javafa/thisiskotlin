package net.flow9.thisiskotlin.firebasestorage

import android.Manifest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import net.flow9.thisiskotlin.firebasestorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val storage = Firebase.storage("gs://this-is-android-with-kot-df246.appspot.com/")
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnUpload.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) // 1. 권한 확인
        }

        binding.btnDownload.setOnClickListener {
            downloadImage("images/temp_1636813558758.jpeg")
        }
    }

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            galleryLauncher.launch("image/*") // 2. 이미지 갤러리 런처를 호출
        } else {
            Toast.makeText(baseContext, "외부 저장소 읽기 권한을 승인해야 사용할 수 있습니다", Toast.LENGTH_LONG).show()
        }
    }

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uploadImage(uri) // 3. 스토리지 업로드 메서드 호출
    }

    fun downloadImage(path: String) {
        storage.getReference(path).downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.imageView)
        }.addOnFailureListener {
            Log.e("스토리지", "다운로드 에러=>${it.message}")
        }
    }

    fun uploadImage(uri: Uri) {
        // 1. 경로 + 사용자ID + 밀리초로 파일주소 만들기
        val fullPath   = makeFilePath("images", "temp",uri)
        val imageRef   = storage.getReference(fullPath)  // 2. 스토리지에 저장할 경로 설정 - images/파일명
        val uploadTask = imageRef.putFile(uri)           // 3. 업로드 태스크 생성

        uploadTask.addOnFailureListener {                // 4. 업로드 실행 및 결과 확인
            Log.d("스토리지", "실패=>${it.message}")

        }.addOnSuccessListener { taskSnapshot ->
            Log.d("스토리지", "성공 주소=>${fullPath}")
        }
    }

    fun makeFilePath(path:String, userId:String, uri:Uri): String {
        val mimeType = contentResolver.getType(uri)?:"/none"     // 마임타입 예) images/jpeg
        val ext = mimeType.split("/")[1]               // 확장자  예) jpeg
        val timeSuffix = System.currentTimeMillis()              // 시간값   예) 1232131241312
        val filename = "${path}/${userId}_${timeSuffix}.${ext}"  // 완성
        return filename                                          // 예) 경로/사용자ID_1232131241312.jpeg
    }
}
