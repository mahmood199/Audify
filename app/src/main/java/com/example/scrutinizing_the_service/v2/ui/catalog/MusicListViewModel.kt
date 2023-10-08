package com.example.scrutinizing_the_service.v2.ui.catalog

import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.data.toSong
import com.example.scrutinizing_the_service.v2.connection.NetworkConnectivityObserver
import com.example.scrutinizing_the_service.v2.data.repo.implementations.MusicRepositoryImpl
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import com.example.scrutinizing_the_service.v2.media3.PlayerEvent
import com.example.scrutinizing_the_service.v2.media3.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val musicRepository: MusicRepositoryImpl,
    private val playerController: PlayerController,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _songs by savedStateHandle.saveable { mutableStateOf(listOf<Song>()) }
    val songs: List<Song>
        get() = _songs

    private val _state = MutableStateFlow(MusicListViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            playerController.audioState.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> calculateProgressValue(
                        currentProgress = playerState.progress,
                        duration = playerState.duration
                    )

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
                    is PlayerState.Playing -> _state.value.isPlaying = playerState.isPlaying
                    is PlayerState.Progress -> calculateProgressValue(
                        currentProgress = playerState.progress,
                        duration = playerState.duration
                    )

                    is PlayerState.Ready -> {
                        _state.value.duration = playerState.duration
                    }
                }
            }
        }

        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                networkConnectivityObserver.observe().collectLatest {
                    _state.value = _state.value.copy(networkStatus = it)
                }
            }
        }
    }

    fun getDeviceAudios() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(loading = true)
            val fetchedSongs = musicRepository.getMusicV4()
            _songs = fetchedSongs
            setMediaItems(_songs)
            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun setMediaItems(songs: List<Song>) {
        val result = songs.map { song ->
            MediaItem.Builder()
                .setUri(song.path)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(song.artist)
                        .setDisplayTitle(song.name)
                        .setSubtitle(song.artist)
                        .build()
                )
                .build()
        }
        viewModelScope.launch(Dispatchers.Main) {
            playerController.addItems(result)
        }
    }

    private fun calculateProgressValue(currentProgress: Long, duration: Long) {
        val progress = if (currentProgress > 0) currentProgress.toFloat() / duration.toFloat()
        else 0f
        _state.value = _state.value.copy(
            progress =
            progress
        )
        _state.value =
            _state.value.copy(progressString = TimeConverter.getConvertedTime(currentProgress))
    }

    fun sendUIEvent(musicListUiEvent: MusicListUiEvent) {
        viewModelScope.launch {
            when (musicListUiEvent) {
                MusicListUiEvent.PlayPause -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayPause)
                }

                MusicListUiEvent.Rewind -> {
                    playerController.onPlayerEvents(PlayerEvent.Rewind)
                }

                MusicListUiEvent.FastForward -> {
                    playerController.onPlayerEvents(PlayerEvent.FastForward)
                }

                MusicListUiEvent.PlayNextItem -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayNextItem)
                }

                MusicListUiEvent.PlayPreviousItem -> {
                    playerController.onPlayerEvents(PlayerEvent.PlayPreviousItem)
                }

                is MusicListUiEvent.PlaySongAt -> {
                    playerController.onPlayerEvents(PlayerEvent.PlaySongAt(musicListUiEvent.index))
                }

                is MusicListUiEvent.UpdateProgress -> {
                    playerController.onPlayerEvents(
                        PlayerEvent.UpdateProgress(
                            newProgress = musicListUiEvent.newProgress
                        )
                    )
                    _state.value.progress = musicListUiEvent.newProgress
                }
            }
        }
    }

}