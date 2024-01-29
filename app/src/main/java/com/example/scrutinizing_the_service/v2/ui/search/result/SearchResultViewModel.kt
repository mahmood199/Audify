package com.example.scrutinizing_the_service.v2.ui.search.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.local.SearchPreference
import com.example.data.models.toSong
import com.example.data.repo.contracts.UserPreferenceRepository
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.v2.media3.MediaPlayerAction
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import com.example.scrutinizing_the_service.v2.media3.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val playerController: PlayerController,
    private val preferenceRepository: UserPreferenceRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchResultViewState())
    val state = _state.asStateFlow()

    val searchQuery = savedStateHandle.get<String>("query") ?: "NULL"

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
                        calculateProgressValue(
                            currentProgress = playerState.progress,
                            duration = playerState.duration
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

                    is PlayerState.Progress -> calculateProgressValue(
                        currentProgress = playerState.progress,
                        duration = playerState.duration,
                    )

                    is PlayerState.Ready -> {
                        _state.value = _state.value.copy(duration = playerState.duration)
                    }
                }
            }
        }

        viewModelScope.launch {
            preferenceRepository.getSearchPreference().collectLatest {
                when (it.searchPreference) {
                    SearchPreference.Track -> {
                        _state.value = _state.value.copy(userSelectedPage = 0)
                    }

                    SearchPreference.Album -> {
                        _state.value = _state.value.copy(userSelectedPage = 1)
                    }

                    SearchPreference.Artist -> {
                        _state.value = _state.value.copy(userSelectedPage = 2)
                    }
                }
            }
        }
    }

    fun setPreference(page: Int) {
        viewModelScope.launch {
            when (page) {
                0 -> preferenceRepository.setSearchPreference(SearchPreference.Track)
                1 -> preferenceRepository.setSearchPreference(SearchPreference.Album)
                2 -> preferenceRepository.setSearchPreference(SearchPreference.Artist)
            }
        }
    }

    private fun calculateProgressValue(currentProgress: Long, duration: Long) {
        val progress = if (currentProgress > 0) currentProgress.toFloat() / duration.toFloat()
        else 0f
        _state.value = _state.value.copy(
            progress = progress,
            progressString = TimeConverter.getConvertedTime(currentProgress),
        )
    }

    fun sendMediaAction(action: MediaPlayerAction) {
        viewModelScope.launch {
            playerController.sendMediaEvent(action)
        }
    }

    fun sendUiEvent(searchResultUiEvent: SearchResultUiEvent) {
        when (searchResultUiEvent) {
            is SearchResultUiEvent.UpdateProgress -> {
                _state.value.progress = searchResultUiEvent.newProgress
            }
        }
    }

}