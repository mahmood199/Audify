package com.example.data.color

import android.content.Context
import android.content.res.AssetManager
import com.example.audify.color.ColorManager
import com.example.audify.color.MyColor
import com.google.gson.JsonSyntaxException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.FileNotFoundException

class ColorManagerTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager

    lateinit var colorManager: ColorManager

    @Before
    fun setUp() {
        val testStream = ColorManagerTest::class.java.getResourceAsStream("/colors.json")
        colorManager = ColorManager()
        MockitoAnnotations.openMocks(this)
        Mockito.doReturn(assetManager).`when`(context).assets
        Mockito.`when`(context.assets.open(Mockito.anyString())).thenReturn(testStream)
    }

    @After
    fun tearDown() {
    }

    @Test(expected = FileNotFoundException::class)
    fun populateColorsFromAssets_inputEmptyFileName_outputException() {
        val fileName = ""
        colorManager.populateColorsFromAssets(context, fileName)
    }

    @Test(expected = JsonSyntaxException::class)
    fun populateColorsFromAssets_inputInvalidJson_outputException() {
        val fileName = "malformed_colors.json"
        colorManager.populateColorsFromAssets(context, fileName)
    }


    @Test
    fun populateColorsFromAssets_inputValidFileName_outputCount() {
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