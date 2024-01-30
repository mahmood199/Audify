package com.example.audify.v2.ui.home.playlist

import androidx.lifecycle.ViewModel
import com.example.data.repo.contracts.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val songsRepository: SongsRepository
) : ViewModel() {

    fun createPlaylist() {}


}