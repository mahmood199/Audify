package com.example.scrutinizing_the_service.v2.ui.search_result

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.remote.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.Artist
import com.example.scrutinizing_the_service.v2.data.models.remote.Track
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SearchRepositoryImpl
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val repositoryImpl: SearchRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SearchResultViewState())

    val state = _state.asStateFlow()

    val searchQuery = savedStateHandle.get<String>("query") ?: "NULL"

    val _albums = mutableStateListOf<Album>()
    val album = _albums.toList()



    private val _artists = mutableStateListOf<Artist>()
    val artist = _artists.toList()

    private val _tracks = mutableStateListOf<Track>()
    val tracks = _tracks.toList()

    init {
        searchForAlbum()
        searchForArtist()
        searchForTrack()
    }

    private fun searchForAlbum() {
        viewModelScope.launch {
            when (val result = repositoryImpl.searchAlbum(searchQuery)) {
                is NetworkResult.Success -> {
                    //_albums.addAll(result.data.albums.album)
                }

                else -> {}
            }
        }
    }

    private fun searchForArtist() {
        viewModelScope.launch {
            when (val result = repositoryImpl.searchArtist(searchQuery)) {
                is NetworkResult.Success -> {
                    //_artists.addAll(result.data.topArtists.artist)
                }

                else -> {}
            }
        }
    }

    private fun searchForTrack() {
        viewModelScope.launch {
            when (val result = repositoryImpl.searchTrack(searchQuery)) {
                is NetworkResult.Success -> {
                    //_tracks.addAll(result.data.tracks.track)
                }

                else -> {}
            }
        }
    }


}