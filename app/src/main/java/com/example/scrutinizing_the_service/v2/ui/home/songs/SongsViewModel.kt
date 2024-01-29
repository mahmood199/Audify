package com.example.scrutinizing_the_service.v2.ui.home.songs

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.data.models.local.Genre
import com.example.data.models.local.RecentlyPlayed
import com.example.data.repo.contracts.GenreRepository
import com.example.data.repo.contracts.SongsRepository
import com.example.scrutinizing_the_service.v2.media3.MediaPlayerAction
import com.example.scrutinizing_the_service.v2.media3.PlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _songs = MutableStateFlow<List<RecentlyPlayed>>(emptyList())
    val songs: StateFlow<List<RecentlyPlayed>> = _songs.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    init {
        observeLocalDB()
    }

    private fun observeLocalDB() {
        viewModelScope.launch(Dispatchers.IO) {
            songsRepository.observeMostRecentlyPlayed().collectLatest {
                _songs.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            genreRepository.getAll().collectLatest {
                _genres.value = it
                if (it.isNotEmpty()) {
                    searchSongByGenre(it.first().name)
                }
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

    fun sendMediaAction(action: MediaPlayerAction) {
        viewModelScope.launch {
            playerController.sendMediaEvent(action)
        }
    }

    fun playItem(recentlyPlayed: RecentlyPlayed) {
        // Make Mapper for this
        val item = MediaItem.Builder()
            .setUri(recentlyPlayed.downloadUrl)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setAlbumArtist(recentlyPlayed.albumName)
                    .setDisplayTitle(recentlyPlayed.name)
                    .setSubtitle(recentlyPlayed.releaseDate)
                    .setArtworkUri(Uri.parse(recentlyPlayed.imageUrl))
                    .setReleaseYear(
                        try {
                            recentlyPlayed.year.toInt()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            2000
                        }
                    )
                    .setAlbumTitle(recentlyPlayed.albumName)
                    .setTitle(recentlyPlayed.name)
                    .build()
            )
            .build()
        sendMediaAction(MediaPlayerAction.SetMediaItem(item))
        viewModelScope.launch(Dispatchers.IO) {
            songsRepository.insertSongs(listOf(recentlyPlayed.copy(playCount = recentlyPlayed.playCount + 1)))
        }
    }

    fun updateFavourite(recentlyPlayed: RecentlyPlayed) {
        viewModelScope.launch(Dispatchers.IO) {
            songsRepository.updateFavourite(recentlyPlayed.id, recentlyPlayed.isFavorite)
        }
    }

}