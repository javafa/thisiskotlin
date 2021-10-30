package com.example.fileio

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fileio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE
        , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    lateinit var launcher:ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    fun checkPermission() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if(result.all {it.value}) {
                startProcess()
            } else {
                // 권한이 없으면 종료
                finish()
            }
        }
        launcher.launch(permissions)
    }

    fun startProcess() {
        setContentView(binding.root)

        val dirPath = filesDir.absolutePath
        val fileName = "NewFile.txt"
        val fileUtil = FileUtil()

        binding.btnWrite.setOnClickListener {
            val content = binding.editText.text.toString().trim()
            if(content.length > 0) {
                Log.d("FileUtil", "dirPath=$dirPath")
                fileUtil.writeTextFile(dirPath, fileName, content)
                binding.editText.setText("")
            }
        }

        binding.btnRead.setOnClickListener {
            val fullPath = "$dirPath/$fileName"
            Log.d("FileUtil", "fullPath=$fullPath")
            val content = fileUtil.readTextFile(fullPath)
            binding.editText.setText(content)
        }
    }

    fun getExternalDirectory() : String? {
        var directory:String? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
        } else {
            directory = "${Environment.getExternalStorageDirectory().absolutePath}/Documents"
        }

        Log.d("FileUtil", "external dir=${directory}")

        return directory
    }
}
