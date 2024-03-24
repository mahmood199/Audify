package com.example.audify.v2.ui.genre

import com.example.data.repo.contracts.GenreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GenresViewModelTest {

    @Mock
    lateinit var genreRepository: GenreRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getGenres() = runTest {
        val genreRepository = genreRepository


    }

    @Test
    fun getSelectedGenres() {
    }

    @Test
    fun addRemoveToSelectedItems() {
    }

    @Test
    fun confirmGenreSelection() {
    }

    @After
    fun tearDown() {

    }
}