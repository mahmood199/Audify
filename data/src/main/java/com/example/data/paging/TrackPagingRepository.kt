package com.example.data.paging

import javax.inject.Inject


class TrackPagingRepository @Inject constructor(
    val remoteDataSource: TrackPagingSource
) {

    fun setQuery(searchedQuery: String) {
        remoteDataSource.setQuery(searchedQuery = searchedQuery)
    }

}