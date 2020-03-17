package com.example.contentresolver

import android.media.MediaPlayer
import android.net.Uri
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class MusicRecyclerAdapter : RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {
    var musicList = mutableListOf<Music>()
    var mediaPlayer:MediaPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        holder.setMusic(music)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var musicUri: Uri? = null

        init {
            itemView.setOnClickListener {
                if(mediaPlayer != null) {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer = MediaPlayer.create(itemView.context, musicUri)
                mediaPlayer?.start()
            }
        }

        fun setMusic(music:Music) {
            itemView.imageAlbum.setImageURI(music.getAlbumUri())
            itemView.textArtist.text = music.artist
            itemView.textTitle.text = music.title

            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            itemView.textDuration.text = duration

            this.musicUri = music.getMusicUri()
        }
    }
}
