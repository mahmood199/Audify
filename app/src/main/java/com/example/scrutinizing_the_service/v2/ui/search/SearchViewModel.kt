package com.example.scrutinizing_the_service.v2.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
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
class SearchViewModel @Inject constructor(
    private val repository: SearchRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(SearchViewState())

    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")

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
                viewModelScope.launch {
                    repository.searchAlbum(album = it)
                    repository.searchArtist(artist = it)
                    repository.searchTrack(track = it)
                }
            }
        }
    }

    fun updateSearchQuery(it: String) {
        _query.value = it
    }


}