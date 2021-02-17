package com.example.foregroundservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener {
            val intent = Intent(this, Foreground::class.java)
            ContextCompat.startForegroundService(this, intent)
        }

        buttonStop.setOnClickListener {
            val intent = Intent(this, Foreground::class.java)
            stopService(intent)
        }
    }
}
