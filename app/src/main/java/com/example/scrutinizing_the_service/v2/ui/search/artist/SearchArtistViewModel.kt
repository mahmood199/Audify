package com.example.scrutinizing_the_service.v2.ui.search.artist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.paging.ArtistPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(
    private val repository: ArtistPagingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.get<String>("query") ?: "NULL"

    init {
        Log.d("SearchSearch2", searchQuery)
        initiateSearch(query = searchQuery)
    }

    val artists = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 20),
        pagingSourceFactory = {
            repository.remoteDataSource
        }
    ).flow
        .cachedIn(viewModelScope)

    fun initiateSearch(query: String) {
        repository.setQuery(query)
    }

}