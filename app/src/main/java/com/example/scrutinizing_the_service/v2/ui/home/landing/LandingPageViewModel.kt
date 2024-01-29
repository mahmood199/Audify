package com.example.scrutinizing_the_service.v2.ui.home.landing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.core.NetworkResult
import com.example.data.repo.implementations.LandingPageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow(LandingPageViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when(val result = landingPageRepository.getLandingPageData()) {
                is NetworkResult.Success -> {
                    Log.d("LandingPageViewModel", result.data.status)
                }
                else -> {}
            }
        }
    }


}