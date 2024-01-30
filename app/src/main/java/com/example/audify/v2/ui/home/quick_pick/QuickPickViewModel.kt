package com.example.audify.v2.ui.home.quick_pick

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audify.v2.media3.PlayerController
import com.example.data.models.local.Artist2
import com.example.data.models.remote.saavn.Album
import com.example.data.models.remote.saavn.ArtistData
import com.example.data.models.remote.saavn.Playlist
import com.example.data.models.remote.saavn.Song
import com.example.data.remote.core.NetworkResult
import com.example.data.repo.contracts.ArtistsRepository
import com.example.data.repo.contracts.GenreRepository
import com.example.data.repo.implementations.LandingPageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickPickViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepositoryImpl,
    private val artistsRepository: ArtistsRepository,
    private val genreRepository: GenreRepository,
    private val controller: PlayerController
) : ViewModel() {

    private val _state = MutableStateFlow(QuickPickViewState())
    val state = _state.asStateFlow()

    val albums = mutableStateListOf<Album>()
    val songs = mutableStateListOf<Song>()
    val artists = mutableStateListOf<ArtistData>()
    val playlists = mutableStateListOf<Playlist>()

    var localArtist = mutableStateListOf<Artist2>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
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

        viewModelScope.launch(Dispatchers.IO) {
            artistsRepository.getAll().collectLatest {
                localArtist.clear()
                localArtist.addAll(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            genreRepository.getAll().collectLatest {
                _state.emit(_state.value.copy(genreCount = it.size))
            }
        }
    }

    private suspend fun extractImageForArtists(albums: List<Album>) {
        albums.forEach { album ->
            album.primaryArtists.forEachIndexed { index, artist ->
                delay(1000)
                if (localArtist.map { it.id }.contains(element = artist.id).not()) {
                    artistsRepository.getArtistInfo(artist.url)
                }
            }
        }
    }

}