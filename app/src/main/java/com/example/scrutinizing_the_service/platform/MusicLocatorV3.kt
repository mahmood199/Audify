package com.example.scrutinizing_the_service.platform

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.scrutinizing_the_service.data.Song

object MusicLocatorV3 {

    const val TAG = "MusicLocatorV3"

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
        MediaStore.Audio.Media.BUCKET_ID
    )
    private const val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

    private val externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    fun getAllAudioContent(
        context: Context,
        contentLocation: Uri = externalContentUri
    ): ArrayList<Song> {
        val allAudioContent = ArrayList<Song>()
        val cursor = context.contentResolver.query(
            contentLocation,
            projections,
            null,
            null,
            "LOWER (" + MediaStore.Audio.Media.TITLE + ") ASC"
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                    )
                    val title = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    )

                    val id: Long = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    )

                    val audioPath = Uri.withAppendedPath(contentLocation, id.toString())

                    val path: String = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    )

                    val size = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                    )

                    val album = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.ALBUM
                        )
                    )

                    val duration = cursor.getLong(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.DURATION
                        )
                    )

                    val albumId: Long = cursor.getLong(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    )

                    val artist = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.Audio.Media.ARTIST
                        )
                    )

                    val composer = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)
                    )

                    allAudioContent.add(
                        Song(
                            name,
                            artist,
                            false,
                            path
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return allAudioContent
    }


}