package com.example.scrutinizing_the_service.v2.ui.search.album

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.paging.AlbumPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchAlbumViewModel @Inject constructor(
    private val repository: AlbumPagingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.get<String>("query") ?: "NULL"

    init {
        Log.d("SearchSearch1", searchQuery)
        initiateSearch(query = searchQuery)
    }

    val albums = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 20),
        pagingSourceFactory = {
            repository.remoteDataSource
        }
    ).flow
        .cachedIn(viewModelScope)

    private fun initiateSearch(query: String) {
        repository.setQuery(query)
    }

}