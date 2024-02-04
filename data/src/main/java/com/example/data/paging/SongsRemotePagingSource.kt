package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.models.remote.saavn.Song
import com.example.data.remote.core.NetworkResult
import com.example.data.remote.saavn.SongsRemoteDataSource
import javax.inject.Inject

class SongsRemotePagingSource @Inject constructor(
    private val dataSource: SongsRemoteDataSource
) : PagingSource<Int, Song>() {

    private var query = ""

    fun setQuery(searchedQuery: String) {
        query = searchedQuery
    }

    override fun getRefreshKey(state: PagingState<Int, Song>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Song> {
        val nextPageNumber = params.key ?: 1

        val result =
            when (val response = dataSource.getSongsByGenres(query, nextPageNumber)) {
                is NetworkResult.Exception -> {
                    LoadResult.Error(response.e)
                }

                is NetworkResult.RedirectError -> {
                    LoadResult.Error(response.e)
                }

                is NetworkResult.ServerError -> {
                    LoadResult.Error(response.e)
                }

                is NetworkResult.Success -> {
                    LoadResult.Page(
                        response.data.data.results,
                        prevKey = null,
                        nextKey = nextPageNumber + 1
                    )
                }

                is NetworkResult.UnAuthorised -> {
                    LoadResult.Error(response.e)
                }
            }

        return result
    }


}