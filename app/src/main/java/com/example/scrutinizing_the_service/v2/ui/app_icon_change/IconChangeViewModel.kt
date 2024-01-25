package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.scrutinizing_the_service.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class IconChangeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(IconChangeViewState.default())
    val state = _state.asStateFlow()

    fun addIconsToState() {
        val icons = listOf(
            IconModel(R.mipmap.ic_app_launcher_v1_foreground, Color(R.color.ic_app_launcher_v1_background)),
            IconModel(R.mipmap.ic_app_launcher_v2_foreground, Color(R.color.ic_app_launcher_v2_background)),
            IconModel(R.mipmap.ic_app_launcher_v3_foreground, Color(R.color.ic_app_launcher_v3_background)),
            IconModel(R.mipmap.ic_app_launcher_v4_foreground, Color(R.color.ic_app_launcher_v5_background)),
            IconModel(R.mipmap.ic_app_launcher_v5_foreground, Color(R.color.ic_app_launcher_v5_background)),
            IconModel(R.mipmap.ic_app_launcher_v6_foreground, Color(R.color.ic_app_launcher_v6_background)),
        )
        _state.value = _state.value.copy(icons = icons)
    }

}