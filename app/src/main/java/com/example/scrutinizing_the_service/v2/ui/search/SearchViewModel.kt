package com.example.scrutinizing_the_service.v2.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.repo.implementations.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(SearchViewState())

    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            repository.searchAlbum("Bitter sweet symphony")
            repository.searchArtist("Bitter sweet symphony")
            repository.searchTrack("Bitter sweet symphony")
        }
    }

    fun updateSearchQuery(it: String) {
        _state.value = _state.value.copy(query = it)
    }


}