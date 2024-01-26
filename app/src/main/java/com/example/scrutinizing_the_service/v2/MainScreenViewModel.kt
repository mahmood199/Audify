package com.example.scrutinizing_the_service.v2

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.v2.constants.BundleKeys
import com.example.scrutinizing_the_service.v2.ui.short_cut.ShortcutType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val shortcutType = mutableStateOf<ShortcutType?>(null)

    fun parseShortcutType(intent: Intent) {
        viewModelScope.launch(Dispatchers.IO) {
            val receivedType =
                intent.extras?.getString(BundleKeys.SHORT_CUT_TYPE) ?: ShortcutType.DEFAULT_VALUE

            val type = when (receivedType) {
                ShortcutType.Dynamic.tag -> ShortcutType.Dynamic
                ShortcutType.Static.tag -> ShortcutType.Static
                ShortcutType.Pinned.tag -> ShortcutType.Pinned
                else -> null
            }
            shortcutType.value = type
        }
    }

}