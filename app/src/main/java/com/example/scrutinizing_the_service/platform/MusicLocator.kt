package com.example.scrutinizing_the_service.platform

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.example.scrutinizing_the_service.data.Song
import java.io.File

object MusicLocator {

    private const val PAGE_SIZE = 10
    private const val AUDIO_PAGE_SIZE = 10

    fun getAllMusicFilesInDevice(
        mContext: Context,
        offset: Int,
        searchText: String
    ): List<Song> {
        val tempAudioList: MutableList<Song> = ArrayList()
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        else
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,
        )
        val selection = MediaStore.Audio.Media.DISPLAY_NAME + " LIKE ?"
        val selectionArgs = arrayOf(searchText)
        val sortOrder =
            "${MediaStore.Audio.Media.DISPLAY_NAME} ASC LIMIT $PAGE_SIZE OFFSET $offset"
        val c = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val limit = Bundle().apply {
                // Limit & Offset
                putInt(ContentResolver.QUERY_ARG_LIMIT, AUDIO_PAGE_SIZE)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
            }
            mContext.contentResolver.query(uri, projection, limit, null)
        } else
            mContext.contentResolver.query(
                uri,
                projection,
                null,
                null,
                "${MediaStore.Audio.Media.DISPLAY_NAME} ASC LIMIT $AUDIO_PAGE_SIZE OFFSET $offset"
            )
        if (c != null) {
            while (c.moveToNext()) {
                try {
                    val path: String = c.getString(0)
                    val songName = c.getString(1)
                    val album: String = c.getString(2)
                    val artist: String = c.getString(3)
                    val extension = File(path).extension
                    if (isMusicFile(extension)) {
                        val audioModel =
                            Song(
                                name = songName,
                                isFavourite = false,
                                artist = artist,
                            )
                        tempAudioList.add(audioModel)
                    }
                } catch (ex: Exception) {

                }
            }
            c.close()
        }
        return tempAudioList
    }

    private fun isMusicFile(extension: String): Boolean {
        return extension.equals("mp3", true)
    }


}