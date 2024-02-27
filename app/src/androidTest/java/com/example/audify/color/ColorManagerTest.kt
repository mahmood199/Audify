package com.example.audify.color

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.gson.JsonSyntaxException
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class ColorManagerTest {

    lateinit var colorManager: ColorManager

    @Before
    fun setUp() {
        colorManager = ColorManager()
    }

    @After
    fun tearDown() {
    }

    @Test(expected = FileNotFoundException::class)
    fun populateColorsFromAssets_inputEmptyFileName_outputException() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fileName = ""
        colorManager.populateColorsFromAssets(context, fileName)
    }

    @Test(expected = JsonSyntaxException::class)
    fun populateColorsFromAssets_inputInvalidJson_outputException() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fileName = "malformed_colors.json"
        colorManager.populateColorsFromAssets(context, fileName)
    }


    @Test
    fun populateColorsFromAssets_inputValidFileName_outputCount() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fileName = "colors.json"
        colorManager.populateColorsFromAssets(context, fileName)
        assertEquals(7, colorManager.colorsList.size)
    }

    @Test
    fun previousQuote_outputCorrectQuote() {
        colorManager.populateColors(
            colors = arrayOf(
                MyColor(color = "red", value = "#000"),
                MyColor(color = "green", value = "#0f0"),
                MyColor(color = "blue", value = "#00f"),
            )
        )
        val previous = colorManager.getPreviousColor()
        assertEquals(previous, MyColor(color = "blue", value = "#00f"))
    }

    @Test
    fun nextQuote_outputCorrectQuote() {
        colorManager.populateColors(
            colors = arrayOf(
                MyColor(color = "red", value = "#000"),
                MyColor(color = "green", value = "#0f0"),
                MyColor(color = "blue", value = "#00f"),
            )
        )
        val previous = colorManager.getNextColor()
        assertEquals(previous, MyColor(color = "green", value = "#0f0"))
    }

}