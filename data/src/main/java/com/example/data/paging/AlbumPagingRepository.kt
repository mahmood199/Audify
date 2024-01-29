package com.example.data.paging

import javax.inject.Inject


class AlbumPagingRepository @Inject constructor(
    val remoteDataSource: AlbumPagingSource
) {

    fun setQuery(searchedQuery: String) {
        remoteDataSource.setQuery(searchedQuery = searchedQuery)
    }

}