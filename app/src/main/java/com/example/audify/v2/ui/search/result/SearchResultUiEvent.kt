package com.example.audify.v2.ui.search.result


sealed class SearchResultUiEvent {
    data class UpdateProgress(val newProgress: Float) : SearchResultUiEvent()
}