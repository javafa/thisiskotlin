package com.example.threadwithui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.threadwithui.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        thread(start=true) {
            var i = 0
            while (i < 10) {
                // UI에 접근할 때는 runOnUiThread 블럭으로 감싸줘야 한다
                runOnUiThread {
                    binding.textView.text = "$i"
                }
                i += 1
                Thread.sleep(1000)
            }
        }
    }
}
