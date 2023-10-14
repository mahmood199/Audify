package com.example.scrutinizing_the_service.v2.ui.home.quick_pick

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistData
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Playlist
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import com.example.scrutinizing_the_service.v2.data.repo.implementations.LandingPageRepositoryImpl
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickPickViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow(QuickPickViewState())
    val state = _state.asStateFlow()

    val albums = mutableStateListOf<Album>()
    val songs = mutableStateListOf<Song>()
    val artists = mutableStateListOf<ArtistData>()
    val playlists = mutableStateListOf<Playlist>()

    init {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isLoading = true))
            when (val result = landingPageRepository.getLandingPageData()) {
                is NetworkResult.Success -> {
                    Log.d("LandingPageViewModel", result.data.status)
                    _state.emit(_state.value.copy(isLoading = false))

                    result.data.data?.albums?.let {
                        albums.addAll(it)
                    }

                    result.data.data?.trending?.songs?.forEach { song ->
                        songs.add(song)
                    }

                    result.data.data?.playlists?.forEach {
                        playlists.add(it)
                    }

                    result.data.data?.albums?.let {
                        extractImageForArtists(it)
                    }


                }

                else -> {}
            }
        }
    }

    private suspend fun extractImageForArtists(albums: List<Album>) {
        albums.forEach { album ->
            album.primaryArtists.forEachIndexed { index, artist ->
                delay(1000)
                when(val result = landingPageRepository.getArtistInfo(artist.url)) {
                    is NetworkResult.Success -> {
                        artists.add(result.data.data)
                    }
                    else -> {}
                }
            }
        }
    }

}