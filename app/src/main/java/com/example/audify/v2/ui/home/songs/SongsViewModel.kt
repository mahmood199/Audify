package com.example.audify.v2.ui.home.songs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.audify.v2.media3.MediaPlayerAction
import com.example.audify.v2.media3.PlayerController
import com.example.data.mapper.SongToMediaItemMapper
import com.example.data.models.local.Genre
import com.example.data.models.local.RecentlyPlayed
import com.example.data.models.remote.saavn.Song
import com.example.data.paging.SongsRemotePagingRepository
import com.example.data.repo.contracts.GenreRepository
import com.example.data.repo.contracts.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val songsRepository: SongsRepository,
    private val genreRepository: GenreRepository,
    private val songsRemotePagingRepository: SongsRemotePagingRepository,
    private val playerController: PlayerController,
    private val mapper: SongToMediaItemMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SongsViewState())
    val state = _state.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    var songs = createPager()

    init {
        observeGenres()
    }

    private fun createPager(): Flow<PagingData<Song>> {
        return Pager(
            config = PAGING_CONFIG,
            pagingSourceFactory = {
                songsRemotePagingRepository.pagingSource()
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun observeGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genreRepository.getAll().collectLatest {
                _genres.value = it
                if (it.isNotEmpty()) {
                    initPagingSource(it.first())
                }
            }
        }
    }

    private fun initPagingSource(genre: Genre) {
        songsRemotePagingRepository.setQuery(genre.name)
        songs = createPager()
    }

    fun playItem(song: Song) {
        // Make Mapper for this
        val item = mapper.map(song)
        sendMediaAction(MediaPlayerAction.SetMediaItem(item))
    }

    private fun sendMediaAction(action: MediaPlayerAction) {
        viewModelScope.launch {
            playerController.sendMediaEvent(action)
        }
    }

    fun updateFavourite(recentlyPlayed: RecentlyPlayed) {
        viewModelScope.launch(Dispatchers.IO) {
            songsRepository.updateFavourite(recentlyPlayed.id, recentlyPlayed.isFavorite)
        }
    }

    fun setupPagingSource() {

    }

    fun onStop() {

    }

    companion object {
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 8,
            prefetchDistance = 12
        )
    }

}