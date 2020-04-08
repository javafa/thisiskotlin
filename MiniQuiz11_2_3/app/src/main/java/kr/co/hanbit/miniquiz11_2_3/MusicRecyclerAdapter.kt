package kr.co.hanbit.miniquiz11_2_3

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
        var currentMusic:Music? = null

        init {
            itemView.btnPlay.setOnClickListener {
                // playing
                if(currentMusic?.isPlay == false) {
                    if (mediaPlayer != null) {
                        mediaPlayer?.release()
                        mediaPlayer = null
                    }
                    mediaPlayer = MediaPlayer.create(itemView.context, currentMusic?.getMusicUri())
                    mediaPlayer?.start()
                    currentMusic?.isPlay = true
                } else {
                    mediaPlayer?.stop()
                    mediaPlayer = null
                    currentMusic?.isPlay = false
                }
                setPlayButton()
            }
        }

        fun setMusic(music:Music) {
            itemView.imageAlbum.setImageURI(music.getAlbumUri())
            itemView.textArtist.text = music.artist
            itemView.textTitle.text = music.title

            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            itemView.textDuration.text = duration
            currentMusic = music
            setPlayButton()
        }

        // setting play button image
        fun setPlayButton() {
            if(currentMusic?.isPlay == false) {
                itemView.btnPlay.setImageResource(android.R.drawable.ic_media_play)
            } else {
                itemView.btnPlay.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        // 음악이 플레이 중에 다른 음악을 플레이 하면 기존 음악의 플레이 버튼을 변경하는 로직이 있어야 합니다.
        // 작성해 보세요
    }
}
