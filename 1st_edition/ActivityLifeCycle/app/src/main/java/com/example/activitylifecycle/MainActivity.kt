package com.example.activitylifecycle


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "MainActivity"
    private lateinit var mediaSession: MediaSessionCompat

    private val mediaCallback = object : MediaSessionCompat.Callback() {
        override fun onPrepare() {
            super.onPrepare()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

            buttonPlay.setOnClickListener {
                val videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")

                videoView.setVideoURI(videoUri)
                videoView.requestFocus()
                videoView.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        textView.text = "액티비티가 실행중입니다."
    }
}


