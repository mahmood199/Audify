package com.example.scrutinizing_the_service.v2.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.data.ToMediaItem
import com.example.scrutinizing_the_service.v2.data.MusicRepository
import com.example.scrutinizing_the_service.v2.media3.Controller
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {

    var songs: List<Song> = mutableListOf()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun getDeviceAudios() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            songs = musicRepository.getMusic()
            Controller.addItems(songs.map {
                it.ToMediaItem()
            })
            _loading.value = false
        }
    }

}