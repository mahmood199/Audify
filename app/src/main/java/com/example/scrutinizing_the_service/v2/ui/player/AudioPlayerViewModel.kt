package com.example.scrutinizing_the_service.v2.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.toSong
import com.example.scrutinizing_the_service.v2.ext.calculateProgressValue
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import com.example.scrutinizing_the_service.v2.media3.PlayerEvent
import com.example.scrutinizing_the_service.v2.media3.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    fun sendUIEvent(playerUiEvent: PlayerUiEvent) {
        viewModelScope.launch {
            when (playerUiEvent) {
                PlayerUiEvent.PlayPause -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayPause)
                }

                PlayerUiEvent.Rewind -> {
                    playerController.onPlayerEvents(PlayerEvent.Rewind)
                }

                PlayerUiEvent.FastForward -> {
                    playerController.onPlayerEvents(PlayerEvent.FastForward)
                }

                PlayerUiEvent.PlayNextItem -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayNextItem)
                }

                PlayerUiEvent.PlayPreviousItem -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayPreviousItem)
                }

                is PlayerUiEvent.PlaySongAt -> {
                    playerController.onPlayerEvents(PlayerEvent.PlaySongAt(playerUiEvent.index))
                }

                is PlayerUiEvent.UpdateProgress -> {
                    playerController.onPlayerEvents(
                        PlayerEvent.UpdateProgress(
                            newProgress = playerUiEvent.newProgress
                        )
                    )
                    _state.value.progress = playerUiEvent.newProgress
                }
            }
        }
    }


}