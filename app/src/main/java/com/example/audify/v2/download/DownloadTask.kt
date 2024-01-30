package com.example.audify.v2.download

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadTask(private val url: String, private val fileName: String) {

    suspend fun start(
        updateProgress: suspend (Float, Long) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                client
                    .newCall(request)
                    .execute()
                    .use { response ->
                        if (response.isSuccessful) {
                            response.body?.run {
                                val contentLengthHeader = response.header("Content-Length") ?: "1L"
                                saveFile(
                                    body = this,
                                    fileName = fileName,
                                    updateProgress = updateProgress,
                                    fileSize = contentLengthHeader.toLong()
                                )
                            }
                        } else {
                            Log.d("DownloadPurpose", "Unsuccessful response")
                        }
                    }
            } catch (e: IOException) {
                Log.d("DownloadPurpose", "Some Error occurred while downloading")
                e.printStackTrace()
            }
        }
    }

    private suspend fun saveFile(
        body: ResponseBody,
        fileName: String,
        updateProgress: suspend (Float, Long) -> Unit,
        fileSize: Long
    ) {
        withContext(context = Dispatchers.IO) {

            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "$fileName.mp3"
            )

            val inputStream = body.byteStream()
            var outputStream: FileOutputStream? = null

            try {
                outputStream = FileOutputStream(file)
                val buffer = ByteArray(fileSize.toInt())
                var bytesRead: Int
                var currentByteReadCount = 0

                do {
                    bytesRead = inputStream.read(buffer)
                    if (bytesRead != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        currentByteReadCount += bytesRead
                        val progress = (currentByteReadCount * 1f) / buffer.size
                        updateProgress(progress, buffer.size.toLong())
                    }
                } while (bytesRead != -1)

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream.close()
                outputStream?.close()
            }
        }
    }

}