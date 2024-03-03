package com.example.audify.v2.ui.e2e

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.audify.v2.activity.AudioPlayerActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EndToEndTesting {

    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val composeTestRule = createAndroidComposeRule<AudioPlayerActivity>()

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    //@get:Rule(order = 1)
    //var testRule = ActivityTestRule(AudioPlayerActivity::class.java, false, false)

    @Before
    fun setUp() {
        Log.d("SampleUITesting", "Setup")
        hiltRule.inject()
      //  testRule.launchActivity(Intent(targetContext, AudioPlayerActivity::class.java))
    }

    @Test
    fun performQuickPickUITest(): Unit = runBlocking {

        composeTestRule.activity.startActivity(Intent(targetContext, AudioPlayerActivity::class.java))

        while (true) {
            composeTestRule.mainClock.advanceTimeByFrame()
        }
    }


}