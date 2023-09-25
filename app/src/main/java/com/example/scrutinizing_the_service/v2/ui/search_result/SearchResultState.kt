package com.example.scrutinizing_the_service.v2.ui.search_result

sealed class SearchResultState {

    data object Success : SearchResultState()
    data object Loading : SearchResultState()
    data object Error : SearchResultState()

}