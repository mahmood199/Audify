package com.example.scrutinizing_the_service.v2.data.repo.implementations

import com.example.scrutinizing_the_service.v2.data.models.response.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.response.ArtistListResponse
import com.example.scrutinizing_the_service.v2.data.models.response.TrackListResponse
import com.example.scrutinizing_the_service.v2.data.remote.SearchRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SearchRepository
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
): SearchRepository {

    override suspend fun searchAlbum(album: String): NetworkResult<AlbumListResponse> {
        return remoteDataSource.searchForAlbum(album = album)
    }

    override suspend fun searchArtist(artist: String): NetworkResult<ArtistListResponse> {
        return remoteDataSource.searchForArtist(artist = artist)
    }

    override suspend fun searchTrack(track: String): NetworkResult<TrackListResponse> {
        return remoteDataSource.searchForTrack(track = track)
    }

}