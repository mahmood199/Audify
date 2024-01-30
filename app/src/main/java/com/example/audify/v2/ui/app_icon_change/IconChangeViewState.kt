package com.example.audify.v2.ui.app_icon_change

data class IconChangeViewState(
    val isLoading: Boolean,
    val icons: Sequence<IconModel>
) {
    companion object {
        fun default(): IconChangeViewState {
            return IconChangeViewState(false, mutableListOf<IconModel>().asSequence())
        }
    }

}