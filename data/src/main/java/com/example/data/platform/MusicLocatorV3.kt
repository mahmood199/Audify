package com.example.data.platform

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.example.data.models.Song

object MusicLocatorV3 {

    private const val TAG = "MusicLocatorV3"

    private val projections = arrayOf(
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ARTIST_ID,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.COMPOSER,
        MediaStore.Audio.Media.SIZE,
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION,
    )


    fun getAllAudio(
        context: Context
    ): ArrayList<Song> {
        val contentLocation = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS + "/"
        )?.toUri()!!

        contentLocation.path?.let { Log.d(TAG, it) }

        val allAudioContent = ArrayList<Song>()
        val cursor = context.contentResolver.query(
            contentLocation,
            projections,
            null,
            null,
             null
        )
        if (cursor != null) {
            Log.d(TAG, "Cursor not null")
            if (cursor.moveToFirst()) {
                do {
                    /*val name = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                    )*/
                    val title = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    )

/*
                    val id: Long = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    )
*/

//                    val audioPath = Uri.withAppendedPath(contentLocation, id.toString())

                    val path: String = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    )

/*
                    val size = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                    )
*/

                    val album = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.ALBUM
                        )
                    )

/*
                    val duration = cursor.getLong(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.DURATION
                        )
                    )

                    val albumId: Long = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    )
*/

                    val artist = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.ARTIST
                        )
                    )

/*
                    val composer = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)
                    )
*/

                    allAudioContent.add(
                        Song(
                            title,
                            artist,
                            album,
                            false,
                            path
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        Log.d(TAG, allAudioContent.size.toString())
        return allAudioContent
    }


}