package com.example.audify.v2.ui.search.result

sealed class SearchResultState {

    data object Success : SearchResultState()
    data object Loading : SearchResultState()
    data object Error : SearchResultState()

}