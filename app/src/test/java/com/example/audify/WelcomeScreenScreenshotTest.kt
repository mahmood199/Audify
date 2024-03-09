package com.example.audify

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.example.audify.v2.theme.AudifyTheme
import com.example.audify.v2.ui.home.songs.SongsUI
import org.junit.Rule
import org.junit.Test

class WelcomeScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        renderingMode = SessionParams.RenderingMode.NORMAL,
        showSystemUi = false,
        maxPercentDifference = 1.0,
    )

    @Test
    fun launchWelcomeScreen_lightTheme() {
        paparazzi.snapshot {
            AudifyTheme(darkTheme = false) {
                SongsUI(
                    navigateToGenreSelection = {

                    },
                    playMusicFromRemote = {

                    }
                )
            }
        }
    }
}