package com.example.scrutinizing_the_service.v2.paging

import javax.inject.Inject


class AlbumPagingRepository @Inject constructor(
    val remoteDataSource: AlbumPagingSource
) {

    fun setQuery(searchedQuery: String) {
        remoteDataSource.setQuery(searchedQuery = searchedQuery)
    }

}