package com.example.scrutinizing_the_service.v2.data.repo.implementations

import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.ArtistListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.TrackListResponse
import com.example.scrutinizing_the_service.v2.data.remote.last_fm.SearchRemoteDataSource
import com.example.scrutinizing_the_service.v2.data.repo.contracts.SearchRepository
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult
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