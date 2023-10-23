package com.example.scrutinizing_the_service.v2.ui.player

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.palette.graphics.Palette
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.toSong
import com.example.scrutinizing_the_service.v2.media3.MediaPlayerAction
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import com.example.scrutinizing_the_service.v2.media3.PlayerState
import com.example.scrutinizing_the_service.v2.util.calculateProgressValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    private val playerController: PlayerController,
) : ViewModel() {

    private val _state = MutableStateFlow(AudioPlayerViewState())
    val state = _state.asStateFlow()

    init {
        val playerState = playerController.audioState.value
        if (playerState is PlayerState.Progress) {
            if (playerState.mediaItem != null) {
                _state.value.currentSong = playerState.mediaItem.toSong()
                _state.value.isPlaying = true
                _state.value.currentMediaItem = playerController.getCurrentlyPlayingItem()
                extractBackgroundColor(playerController.getCurrentlyPlayingItem())
            } else {
                _state.value.isPlaying = false
            }
        }

        viewModelScope.launch {
            playerController.audioState.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> {
                        val result = calculateProgressValue(
                            currentProgress = playerState.progress,
                            duration = playerState.duration
                        )
                        _state.value = _state.value.copy(
                            progress = result.first,
                            progressString = result.second
                        )
                    }

                    is PlayerState.CurrentPlaying -> {
                        if (playerState.mediaItem != null) {
                            _state.value = _state.value.copy(
                                currentSong = playerState.mediaItem.toSong().copy(
                                    duration = playerState.duration.toInt()
                                )
                            )
                        }
                    }

                    PlayerState.Initial -> {}
                    is PlayerState.Playing -> {
                        _state.value.isPlaying = playerState.isPlaying
                    }

                    is PlayerState.Progress -> {
                        val result = calculateProgressValue(
                            currentProgress = playerState.progress,
                            duration = playerState.duration,
                        )
                        _state.value = _state.value.copy(
                            progress = result.first,
                            progressString = result.second,
                            duration = TimeConverter.getConvertedTime(seconds = playerState.duration / 1000)
                        )
                    }

                    is PlayerState.Ready -> {
                        _state.value = _state.value.copy(
                            duration = TimeConverter.getConvertedTime(seconds = playerState.duration / 1000)
                        )
                    }
                }
            }
        }

    }

    private fun extractBackgroundColor(currentlyPlayingItem: MediaItem?) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                currentlyPlayingItem?.mediaMetadata?.artworkUri?.run {
                    val url = URL("$scheme:$schemeSpecificPart")
                    val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    val color = getDominantColor2(image)
                    _state.value = _state.value.copy(backGroundColor = Color(color = color))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getDominantColor2(image: Bitmap): Int {
        // getVibrantColor
        // getLightVibrantColor
        // getDarkVibrantColor
        // getMutedColor
        // getLightMutedColor
        // getDarkMutedColor
        // getDominantColor
        val color = Palette.from(image).generate().getVibrantColor(0)
        return color
    }

    private fun getDominantColor1(bitmap: Bitmap): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    fun sendUIEvent(playerUiEvent: PlayerUiEvent) {
        viewModelScope.launch {
            when (playerUiEvent) {
                is PlayerUiEvent.UpdateProgress -> {
                    _state.value.progress = playerUiEvent.newProgress
                }
            }
        }
    }

    fun sendMediaAction(action: MediaPlayerAction) {
        viewModelScope.launch {
            playerController.sendMediaEvent(action)
        }
    }


}