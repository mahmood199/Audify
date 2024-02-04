package com.example.audify.v2.ui.home.quick_pick

data class QuickPickViewState(
    val isLoading: Boolean,
    val genreCount: Int
) {
    companion object {
        fun default() = QuickPickViewState(false, 0)
    }
}