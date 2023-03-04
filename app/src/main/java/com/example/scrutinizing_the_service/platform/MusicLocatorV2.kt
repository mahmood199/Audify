package com.example.scrutinizing_the_service.platform

import android.content.Context
import android.database.MergeCursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.scrutinizing_the_service.data.Song
import java.util.*


//Some what working. This is lsiting all the device ringtones
object MusicLocatorV2 {

    private const val TAG = "MusicLocatorV2"

    fun getAllAudio(context: Context): List<Song> {
        val files: MutableList<Song> = ArrayList()
        try {
            val columns = arrayOf(
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
            )
            val cursor = MergeCursor(
                arrayOf(
                    context.contentResolver.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        columns,
                        null,
                        null,
                        null
                    ),
                )
            )
            cursor.moveToFirst()
            files.clear()
            while (!cursor.isAfterLast) {
                var path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                var album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val lastPoint = path.lastIndexOf(".")
                path = path.substring(0, lastPoint) + path.substring(lastPoint).lowercase(Locale.getDefault())
                Log.d(TAG, path)
                val duration = getTotalDurationOfAudio(context, path)
                files.add(Song(name, artist, false, path, duration))
                cursor.moveToNext()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return files
    }

    private val mmr = MediaMetadataRetriever()

    private fun getTotalDurationOfAudio(context: Context, pathStr: String): Int {
        val uri = Uri.parse(pathStr)
        mmr.setDataSource(context, uri)
        val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return durationStr?.toInt()?.div(1000) ?: 1
    }


}