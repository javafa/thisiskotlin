package kr.co.hanbit.miniquiz11_2_3

import android.net.Uri
import android.provider.MediaStore

class Music (id: String, title: String?, artist: String?,
             albumId: String?, duration: Long?) {

    var id: String = ""
    var title: String?
    var artist: String?
    var albumId: String?
    var duration: Long?
    var isPlay = false

    init {
        this.id = id
        this.title = title
        this.artist = artist
        this.albumId = albumId
        this.duration = duration
    }

    fun getMusicUri(): Uri {
        return Uri.withAppendedPath(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
        )
    }

    fun getAlbumUri(): Uri {

        return Uri.parse(
            "content://media/external/audio/albumart/" + albumId
        )
    }

}
