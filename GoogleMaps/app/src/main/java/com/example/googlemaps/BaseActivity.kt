package com.example.googlemaps

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class BaseActivity : AppCompatActivity() {

    abstract fun permissionGranted(requestCode: Int)
    abstract fun permissionDenied(requestCode: Int)

    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            val isAllPermissionsGranted = permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }
}