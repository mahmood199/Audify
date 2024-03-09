package com.example.audify.color

import android.content.Context
import com.google.gson.Gson

class ColorManager {

    var colorsList = emptyArray<MyColor>()
    var currentColorIndex = 0

    fun populateColorsFromAssets(context: Context, fileName: String) {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        colorsList = gson.fromJson(json, Array<MyColor>::class.java)
    }

    fun populateColors(colors: Array<MyColor>) {
        colorsList = colors
    }

    fun getCurrentColor(): MyColor {
        return colorsList[currentColorIndex]
    }

    fun getNextColor(): MyColor {
        val myColor = colorsList[(currentColorIndex + 1) % colorsList.size]
        currentColorIndex++
        return myColor
    }

    fun getPreviousColor(): MyColor {
        val myColor = colorsList[(currentColorIndex + colorsList.size - 1) % colorsList.size]
        currentColorIndex--
        return myColor
    }
}