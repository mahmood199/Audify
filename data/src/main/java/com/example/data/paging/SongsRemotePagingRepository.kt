package com.example.data.paging

import javax.inject.Inject

class SongsRemotePagingRepository @Inject constructor(
    private val songsRemotePagingSource: SongsRemotePagingSource
) {

    fun setQuery(query: String) {
        songsRemotePagingSource.setQuery(searchedQuery = query)
    }

    fun pagingSource(): SongsRemotePagingSource {
        return songsRemotePagingSource
    }

}