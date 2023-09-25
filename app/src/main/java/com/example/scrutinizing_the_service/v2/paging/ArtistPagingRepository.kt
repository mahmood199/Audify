package com.example.scrutinizing_the_service.v2.paging

import javax.inject.Inject


class ArtistPagingRepository @Inject constructor(
    val remoteDataSource: ArtistPagingSource
) {

    fun setQuery(searchedQuery: String) {
        remoteDataSource.setQuery(searchedQuery = searchedQuery)
    }

}