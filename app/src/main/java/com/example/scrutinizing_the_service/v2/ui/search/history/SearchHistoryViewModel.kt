package com.example.scrutinizing_the_service.v2.ui.search.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SearchHistoryRepositoryImpl
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepositoryImpl,
    private val searchResultRepositoryImpl: SearchRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(SearchHistoryViewState())

    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")

    val books = searchHistoryRepository.getAll1()

    val uiQuery = _query.asStateFlow()

    private val query: Flow<String> = _query.asStateFlow()
        .filter { query ->
            if (query.isEmpty()) {
                return@filter false
            } else {
                return@filter true
            }
        }
        .debounce(500)
        .distinctUntilChanged()

    init {
        viewModelScope.launch {
            query.collect {
                searchResultRepositoryImpl.searchAlbum(album = it)
                searchResultRepositoryImpl.searchArtist(artist = it)
                searchResultRepositoryImpl.searchTrack(track = it)
            }
        }
    }

    fun updateSearchQuery(it: String) {
        _query.value = it
    }

    fun addToSearchHistory(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchQuery.isNotBlank()) {
                searchHistoryRepository.insert(
                    RecentSearch(
                        query = searchQuery.trim(),
                        timeStamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun deleteSearch(search: RecentSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryRepository.delete(search)
        }
    }


}