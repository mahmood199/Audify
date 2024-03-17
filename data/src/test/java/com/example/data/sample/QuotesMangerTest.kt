package com.example.data.sample

import android.content.Context
import android.content.res.AssetManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations


class QuotesMangerTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test() {
        doReturn(assetManager).`when`(context).assets
        Mockito.`when`(context.assets.open(anyString()))
    }

    @After
    fun tearDown() {

    }

}