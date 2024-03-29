package com.example.audify.v2.ui.search.track

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.paging.TrackPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchTrackViewModel @Inject constructor(
    private val trackPagingRepository: TrackPagingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.get<String>("query") ?: "NULL"

    init {
        Log.d("SearchSearch3", searchQuery)
        initiateSearch(query = searchQuery)
    }

    val tracks = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 20),
        pagingSourceFactory = {
            trackPagingRepository.remoteDataSource
        }
    ).flow
        .cachedIn(viewModelScope)

    fun initiateSearch(query: String) {
        trackPagingRepository.setQuery(query)
    }

}