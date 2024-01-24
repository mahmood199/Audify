package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class IconChangeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(IconChangeViewState.default())
    val state = _state.asStateFlow()

}