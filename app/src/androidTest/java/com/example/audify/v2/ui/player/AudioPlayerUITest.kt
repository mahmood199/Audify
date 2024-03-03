package com.example.audify.v2.ui.player

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.audify.v2.theme.AudifyTheme
import com.example.data.models.Song
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AudioPlayerUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var mediaItem: MediaItem

    @Before
    fun setup() {
        mediaItem = MediaItem
            .Builder()
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist("Dua Lipa")
                    .setArtworkUri(Uri.parse(""))
                    .build()
            )
            .build()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testPlayerUI() = runBlocking {
        composeTestRule.setContent {
            AudifyTheme {

                val state by remember {
                    mutableStateOf(
                        AudioPlayerViewState
                            .default()
                            .copy(
                                isPlaying = true,
                                progress = 0.7f,
                                progressString = "2:01",
                                duration = "3:32",
                                currentMediaItem = mediaItem,
                                currentSong = Song(
                                    name = "One Kiss (2018) By Dua Lipa",
                                    artist = "Dua Lipa",
                                    album = "One Kiss (2018) By Dua Lipa",
                                    isFavourite = true,
                                    duration = 212
                                )
                            )
                    )
                }

                AudioPlayerUI(
                    state = state,
                    backPress = { },
                    sendUiEvent = {},
                    sendMediaAction = {}
                )
            }
        }

        while (true) {
            composeTestRule.mainClock.advanceTimeByFrame()
        }

    }
}