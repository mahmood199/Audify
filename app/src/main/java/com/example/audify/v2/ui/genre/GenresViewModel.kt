package com.example.audify.v2.ui.genre

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.local.Genre
import com.example.data.repo.contracts.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GenresViewState())
    val state = _state.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    val selectedGenres = mutableStateListOf<Genre>()

    init {
        observeLocalDB()
    }

    private fun observeLocalDB() {
        viewModelScope.launch(Dispatchers.IO) {
            genreRepository.getAll().collectLatest {
                _genres.value = it
            }
        }
    }

    fun addRemoveToSelectedItems(selectedGenre: Genre) {
        if (selectedGenres.contains(selectedGenre))
            selectedGenres.remove(selectedGenre)
        else {
            selectedGenres.add(selectedGenre)
            if (_state.value.enforceSelection)
                selectionPrompt(false)
        }
    }

    fun confirmGenreSelection() {
        if (selectedGenres.isEmpty()) {
            selectionPrompt(true)
        } else {
            updateSelectedGenre()
        }
    }

    private fun updateSelectedGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedGenres.forEach { genre ->
                genreRepository.add(genre.copy(userSelected = true))
            }
        }
    }

    private fun selectionPrompt(value: Boolean) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(enforceSelection = value))
        }
    }

}