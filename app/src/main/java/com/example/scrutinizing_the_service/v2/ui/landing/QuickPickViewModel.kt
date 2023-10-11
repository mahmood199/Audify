package com.example.scrutinizing_the_service.v2.ui.landing

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.repo.implementations.LandingPageRepositoryImpl
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickPickViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow(QuickPickViewState())
    val state = _state.asStateFlow()

    val albums = mutableStateListOf<Album>()

    init {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isLoading = true))
            when (val result = landingPageRepository.getLandingPageData()) {
                is NetworkResult.Success -> {
                    Log.d("LandingPageViewModel", result.data.status)
                    delay(2000)
                    _state.emit(_state.value.copy(isLoading = false))
                    result.data.data?.albums?.let {
                        albums.addAll(it)
                    }
                }

                else -> {}
            }
        }
    }

}