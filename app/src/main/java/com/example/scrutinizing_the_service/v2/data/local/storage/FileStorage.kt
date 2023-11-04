package com.example.scrutinizing_the_service.v2.data.local.storage

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun writeToInternalCacheDirectory(context: Context, data: String) {
    val cacheDir = context.cacheDir
    val file = File(cacheDir, "internal_cache.txt")
    writeToFile(file = file, data = data)
}

fun writeToInternalFilesDirectory(context: Context, data: String) {
    val filesDir = context.filesDir
    val file = File(filesDir, "internal_file.txt")
    writeToFile(file = file, data = data)
}

fun writeToExternalCacheDirectory(context: Context, data: String) {
    val filesDir = context.externalCacheDir
    val file = File(filesDir, "external_file.txt")
    writeToFile(file = file, data = data)
}

fun writeToFile(file: File, data: String) {
    try {
        val writer = FileWriter(file)
        writer.write(data)
        writer.flush()
        writer.close()
    } catch (e: IOException) {
        Log.d("writeToFileError","Failed to write to file: ${file.name}")
    }
}

fun readFromInternalCacheDirectory(context: Context): String? {
    val cacheDir = context.cacheDir
    val file = File(cacheDir, "internal_cache.txt")
    return readFromFile(file = file)
}

fun readFromFile(file: File): String? {
    return try {
        val reader = FileReader(file)
        reader.readText()
    } catch (e: IOException) {
        null
    }
}

data class Video(
    val uri: Uri,
    val name: String
)

// Scoped storage
fun retrieveVideosFromStorage(context: Context): List<Video> {
    val videoList = mutableListOf<Video>()
    val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

    val projection = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME)

    val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

    val cursor = context.contentResolver.query(collection, projection, null, null, sortOrder)

    cursor?.use {
        val idColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID)
        val nameColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumnIndex)
            val name = cursor.getString(nameColumnIndex)

            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            val video = Video(uri = uri, name = name)
            videoList.add(video)
        }
    }

    return videoList
}