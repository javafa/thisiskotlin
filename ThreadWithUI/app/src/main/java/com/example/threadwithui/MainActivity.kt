package com.example.threadwithui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread(start=true) {
            var i = 0
            while (i < 10) {
                // UI에 접근할 때는 runOnUiThread 블럭으로 감싸줘야 한
                textView.text = "$i"
                i += 1
                Thread.sleep(1000)
            }
        }
    }
}
