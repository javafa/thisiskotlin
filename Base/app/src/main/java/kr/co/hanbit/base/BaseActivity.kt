package kr.co.hanbit.base

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun permissionGranted()
    abstract fun permissionDenied()

    fun requestPermissions(permissions:Array<String>) {
        requestPermissionLauncher.launch(permissions)
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        if( results.all{ it.value }) {
            permissionGranted()
        } else {
            permissionDenied()
        }
    }
}