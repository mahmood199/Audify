package com.example.scrutinizing_the_service.v2.ui.home.favourites

import androidx.lifecycle.ViewModel
import com.example.data.repo.contracts.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val songsRepository: SongsRepository
) : ViewModel() {



}