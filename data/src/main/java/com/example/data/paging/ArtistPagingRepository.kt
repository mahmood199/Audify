package com.example.data.paging

import javax.inject.Inject


class ArtistPagingRepository @Inject constructor(
    val remoteDataSource: ArtistPagingSource
) {

    fun setQuery(searchedQuery: String) {
        remoteDataSource.setQuery(searchedQuery = searchedQuery)
    }

}