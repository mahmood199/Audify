package com.example.audify.v2.ui.audio_download

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.models.local.DownloadItem
import com.example.data.repo.contracts.SongsRepository
import com.example.data.paging.FileDownloadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadCenterViewModel @Inject constructor(
    private val songsRepository: SongsRepository,
    private val fileDownloadRepository: FileDownloadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.get<String>("query") ?: "Sad"

    var downloadItemsFlow: Flow<PagingData<DownloadItem>> =
        fileDownloadRepository.observePagingSource().cachedIn(viewModelScope)

    init {
        tryGettingItems()
    }

    private fun tryGettingItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val downloadItems = fileDownloadRepository.getItems()
            Log.d("DownloadItemsSize", downloadItems.size.toString())
            downloadItems.forEach {
                Log.d("DownloadItem", it.toString())
            }
        }
    }

    fun clearDownloadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = fileDownloadRepository.clearData()
        }
    }

}