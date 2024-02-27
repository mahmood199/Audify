package com.example.audify.v2.ui.home.landing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.audify.v2.connection.NetworkConnectivityObserver
import com.example.audify.v2.media3.MediaPlayerAction
import com.example.audify.v2.media3.PlayerController
import com.example.audify.v2.media3.PlayerState
import com.example.audify.v2.ui.catalog.MusicListUiEvent
import com.example.audify.v2.util.calculateProgressValue
import com.example.data.models.Song
import com.example.data.models.toSong
import com.example.data.repo.implementations.LandingPageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepositoryImpl,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    private val playerController: PlayerController,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(LandingPageViewState())
    val state = _state.asStateFlow()

    private var _songs by savedStateHandle.saveable { mutableStateOf(listOf<Song>()) }
    val songs: List<Song>
        get() = _songs

    private val stateLock = Mutex()

    init {
        setupPlayerObserver()
        setupNetworkObserver()
    }

    private fun setupPlayerObserver() {
        viewModelScope.launch {
            playerController.audioState.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> calculateProgressValue(
                        currentProgress = playerState.progress,
                        duration = playerState.duration
                    )

                    is PlayerState.CurrentPlaying -> {
                        if (playerState.mediaItem != null) {
                            updateState {
                                copy(
                                    currentSong = playerState.mediaItem.toSong().copy(
                                        duration = playerState.duration.toInt()
                                    )
                                )
                            }
                        }
                    }

                    PlayerState.Initial -> {}
                    is PlayerState.Playing -> {
                        updateState { copy(isPlaying = playerState.isPlaying) }
                    }

                    is PlayerState.Progress -> calculateProgressValue(
                        currentProgress = playerState.progress,
                        duration = playerState.duration
                    )

                    is PlayerState.Ready -> {
                        updateState { copy(duration = playerState.duration) }
                    }
                }
            }

        }
    }

    private fun setupNetworkObserver() {
        viewModelScope.launch {
            networkConnectivityObserver.networkState.collectLatest {
                updateState { copy(isConnected = it) }
            }
        }
    }

    private suspend fun updateState(updater: LandingPageViewState.() -> LandingPageViewState) {
        stateLock.withLock {
            _state.value = _state.value.updater()
        }
    }

    fun sendMediaAction(action: MediaPlayerAction) {
        viewModelScope.launch {
            playerController.sendMediaEvent(action)
        }
    }

    fun sendUIEvent(musicListUiEvent: MusicListUiEvent) {
        when (musicListUiEvent) {
            is MusicListUiEvent.UpdateProgress -> {
                _state.value.progress = musicListUiEvent.newProgress
            }
        }
    }


}