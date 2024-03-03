package com.example.audify

import android.content.Context
import java.io.BufferedReader

object UiTestFileUtils {
    fun readFile(context: Context, filename: String): String {
        return try {
            val bufferedReader = context.assets.open(filename).bufferedReader()
            bufferedReader.use(BufferedReader::readText) // autocloses to prevent resource leaks
        } catch (e: Exception) {
            println("Error reading UiTestFile: $filename: $e")
            ""
        }
    }
}