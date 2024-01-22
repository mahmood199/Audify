package com.example.scrutinizing_the_service.v2.ui.short_cut

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShortcutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val shortcutType = mutableStateOf<ShortcutType?>(null)

}