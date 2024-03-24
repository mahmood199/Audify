package com.example.audify.v2.ui.app_icon_change

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydiver.audify.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class IconChangeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(IconChangeViewState.default())
    val state = _state.asStateFlow()

    private val _stateLiveData = MutableLiveData(IconChangeViewState.default())
    val stateLiveData: LiveData<IconChangeViewState> = _stateLiveData

    private val stateLock = Mutex()

    fun addIconsToState() {
        viewModelScope.launch(Dispatchers.IO) {
            val icons = getIconModels()
            var tempViewState = IconChangeViewState.default()
            updateState {
                tempViewState = copy(icons = icons)
                tempViewState
            }
            _stateLiveData.postValue(
                tempViewState
            )
        }
    }

    fun getIconModels(): Sequence<IconModel> {
        return listOf(
            IconModel(
                R.mipmap.ic_app_launcher_v1_foreground,
                Color(R.color.ic_app_launcher_v1_background),
                IconVariant.Variant1
            ),
            IconModel(
                R.mipmap.ic_app_launcher_v2_foreground,
                Color(R.color.ic_app_launcher_v2_background),
                IconVariant.Variant2
            ),
            IconModel(
                R.mipmap.ic_app_launcher_v3_foreground,
                Color(R.color.ic_app_launcher_v3_background),
                IconVariant.Variant3
            ),
            IconModel(
                R.mipmap.ic_app_launcher_v4_foreground,
                Color(R.color.ic_app_launcher_v5_background),
                IconVariant.Variant4
            ),
            IconModel(
                R.mipmap.ic_app_launcher_v5_foreground,
                Color(R.color.ic_app_launcher_v5_background),
                IconVariant.Variant5
            ),
            IconModel(
                R.mipmap.ic_app_launcher_v6_foreground,
                Color(R.color.ic_app_launcher_v6_background),
                IconVariant.Variant6
            ),
        ).asSequence()
    }

    private suspend fun updateState(updater: IconChangeViewState.() -> IconChangeViewState) {
        stateLock.withLock {
            _state.value = _state.value.updater()
        }
    }

}