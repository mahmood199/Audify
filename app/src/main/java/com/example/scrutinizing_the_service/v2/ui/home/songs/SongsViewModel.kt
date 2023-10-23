package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.repo.contracts.GenreRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SongsRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.UserPreferenceRepository
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val songsRepository: SongsRepository,
    private val genreRepository: GenreRepository,
    private val playerController: PlayerController
) : ViewModel() {

    private val _state = MutableStateFlow(SongsViewState())
    val state = _state.asStateFlow()

    val songs = mutableStateListOf<RecentlyPlayed>()

    val genres = mutableStateListOf<Genre>()

    init {
        observeLocalDB()
    }

    private fun observeLocalDB() {
        viewModelScope.launch {
            songsRepository.observeMostRecentlyPlayed().collectLatest {
                songs.clear()
                songs.addAll(it)
            }
        }

        viewModelScope.launch {
            genreRepository.getAll().collectLatest {
                genres.clear()
                genres.addAll(it)
            }
        }
    }

    private fun searchSongByGenre(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000)
            songsRepository.getSongsByGenres(genre)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun searchSongs(query: String) {
        viewModelScope.launch {

        }
    }

}