package com.example.scrutinizing_the_service.v2.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.Track
import com.example.scrutinizing_the_service.v2.data.remote.last_fm.SearchRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
import javax.inject.Inject

class TrackPagingSource @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : PagingSource<Int, Track>() {

    private var query = ""

    fun setQuery(searchedQuery: String) {
        query = searchedQuery
    }

    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        val nextPageNumber = params.key ?: 1
        val result =
            when (val response = searchRemoteDataSource.searchForTrack(query, nextPageNumber)) {
                is NetworkResult.Exception -> {
                    throw Exception(response.e)
                }

                is NetworkResult.RedirectError -> {
                    throw Exception(response.e)
                }

                is NetworkResult.ServerError -> {
                    throw Exception(response.e)
                }

                is NetworkResult.Success -> {
                    LoadResult.Page(
                        data = response.data.results.trackMatchesList.tracks,
                        prevKey = null, // Only paging forward.
                        nextKey = nextPageNumber + 1
                    )
                }

                is NetworkResult.UnAuthorised -> {
                    throw Exception(response.e)
                }
            }

        return result
    }

    override val keyReuseSupported = true

}