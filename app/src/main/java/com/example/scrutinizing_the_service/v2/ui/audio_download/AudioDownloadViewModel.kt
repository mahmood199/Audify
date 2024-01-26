package com.example.scrutinizing_the_service.v2.ui.audio_download

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import com.example.scrutinizing_the_service.v2.data.repo.contracts.GenreRepository
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SongsRepository
import com.example.scrutinizing_the_service.v2.paging.TrackPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioDownloadViewModel @Inject constructor(
    private val songsRepository: SongsRepository,
    private val genreRepository: GenreRepository,
    private val trackPagingRepository: TrackPagingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.get<String>("query") ?: "Sad"

    val tracks = mutableStateListOf<Song>()

    init {
        Log.d("SearchSearch3", searchQuery)
        initiateSearch(query = searchQuery)
    }

    private fun initiateSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = songsRepository.getSongsByGenresTesting(query)
            when (result) {
                is NetworkResult.Success -> {
                    tracks.addAll(result.data.data.results)
                }
                else -> {

                }
            }
        }
    }

}