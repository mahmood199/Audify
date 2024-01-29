package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.models.remote.last_fm.Album
import com.example.data.remote.last_fm.SearchRemoteDataSource
import com.example.data.remote.core.NetworkResult
import javax.inject.Inject

class AlbumPagingSource @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
) : PagingSource<Int, Album>() {

    private var query = ""

    fun setQuery(searchedQuery: String) {
        query = searchedQuery
    }

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        val nextPageNumber = params.key ?: 1
        val result =
            when (val response = remoteDataSource.searchForAlbum(query, nextPageNumber)) {
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
                        data = response.data.albumsResults.album.albums,
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