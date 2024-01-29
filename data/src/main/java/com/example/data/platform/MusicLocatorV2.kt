package com.example.data.platform

import android.content.Context
import android.database.MergeCursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.data.models.Song
import java.io.File
import java.util.Locale
import kotlin.math.ceil


//Some what working. This is listing all the device ringtones
object MusicLocatorV2 {

    private const val TAG = "MusicLocatorV2"

    private val audioFiles = mutableListOf<Song>()

    private val metadataRetriever = MediaMetadataRetriever()

    fun fetchAllAudioFilesFromDevice(context: Context): List<Song> {
        if (audioFiles.isNotEmpty())
            return audioFiles
        val files: MutableList<Song> = ArrayList()
        val columns = arrayOf(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,


            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.BUCKET_ID,
            MediaStore.Audio.Media.DOCUMENT_ID,
            MediaStore.Audio.Media.ORIGINAL_DOCUMENT_ID,
            MediaStore.Audio.Media.INSTANCE_ID,
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
            path = path.substring(0, lastPoint) + path.substring(lastPoint)
                .lowercase(Locale.getDefault())
            val duration = getTotalDurationOfAudioInSeconds(context, path)
            if (duration > 5) {
                files.add(
                    Song(
                        name = name,
                        artist = artist,
                        album = album,
                        isFavourite = false,
                        path = path,
                        duration = duration
                    )
                )
            }
            cursor.moveToNext()
        }
        audioFiles.addAll(files)
        return files
    }

    private fun getTotalDurationOfAudioInSeconds(context: Context, pathStr: String): Int {
        val file = File(pathStr)
        val uri = Uri.fromFile(file)
        metadataRetriever.setDataSource(context, uri)
        val durationStr =
            metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val result = maxOf(ceil(durationStr?.toFloat()?.div(1000.0F) ?: 0F).toInt(), 1)
        return result
    }

    fun getSize() = audioFiles.size

    fun getAudioFiles() = audioFiles


}