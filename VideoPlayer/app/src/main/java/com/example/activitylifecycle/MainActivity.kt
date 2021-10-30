package com.example.activitylifecycle


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activitylifecycle.databinding.ActivityMainBinding
import android.util.Log


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            buttonPlay.setOnClickListener {
                val videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
                videoView.setVideoURI(videoUri)
            }

            videoView.setOnPreparedListener { mediaPlayer ->
                Log.d(javaClass.simpleName, "video started ===>")
                videoView.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.textView.text = "액티비티가 실행중입니다."
    }
}


