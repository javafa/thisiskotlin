package com.example.activitylifecycle

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activitylifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    var playing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            buttonPlay.setOnClickListener {
                val videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
                videoView.setVideoURI(videoUri)
            }

            videoView.setOnPreparedListener { mediaPlayer ->
                playing = true
                videoView.start()
            }
        }
    }

    override fun onPause() {
        if(playing) {
            binding.textView.text = "동영상 멈춤."
            binding.videoView.pause()
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if(playing) {
            binding.videoView.resume()
            binding.textView.text = "동영상이 실행중입니다."
        }
    }
}


