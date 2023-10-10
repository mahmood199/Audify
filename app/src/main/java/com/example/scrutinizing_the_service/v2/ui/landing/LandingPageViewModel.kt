package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(LandingPageViewState())
    val state = _state.asStateFlow()


}