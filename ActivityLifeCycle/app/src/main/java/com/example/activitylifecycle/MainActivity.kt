package com.example.activitylifecycle


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.MediaController
import com.example.activitylifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    val LOG_TAG = "MainActivity"
    private lateinit var mediaSession: MediaSessionCompat

    private val mediaCallback = object : MediaSessionCompat.Callback() {
        override fun onPrepare() {
            super.onPrepare()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val mediaController = MediaController(this)

        // Create a MediaSessionCompat
        mediaSession = MediaSessionCompat(this, LOG_TAG).apply {

            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

            // Do not let MediaButtons restart the player when the app is not visible
            setMediaButtonReceiver(null)

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())

            // MySessionCallback has methods that handle callbacks from a media controller
            setCallback(mediaCallback)
        }

        // Create a MediaControllerCompat
        MediaControllerCompat(this, mediaSession).also { mediaController ->
            MediaControllerCompat.setMediaController(this, mediaController)

            binding.buttonPlay.setOnClickListener {
                val videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")

                binding.videoView.setVideoURI(videoUri)
                binding.videoView.requestFocus()
                binding.videoView.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.textView.text = "액티비티가 실행중입니다."
    }
}


