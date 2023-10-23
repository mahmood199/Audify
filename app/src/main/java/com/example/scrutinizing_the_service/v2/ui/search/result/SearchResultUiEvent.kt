package com.example.scrutinizing_the_service.v2.ui.search.result


sealed class SearchResultUiEvent {
    data class UpdateProgress(val newProgress: Float) : SearchResultUiEvent()
}