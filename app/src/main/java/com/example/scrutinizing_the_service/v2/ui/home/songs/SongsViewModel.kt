package com.example.scrutinizing_the_service.v2.ui.home.songs

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SongsRepository
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
    private val songsRepository: SongsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SongsViewState())
    val state = _state.asStateFlow()

    val songs = mutableStateListOf<RecentlyPlayed>()

    init {
        searchSongByGenre("Popular Hits")
        observeLocalDB()
    }

    private fun observeLocalDB() {
        viewModelScope.launch {
            songsRepository.observeMostRecentlyPlayed().collectLatest {
                songs += it
                Log.d("SongsViewModel2", it.size.toString())
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

}