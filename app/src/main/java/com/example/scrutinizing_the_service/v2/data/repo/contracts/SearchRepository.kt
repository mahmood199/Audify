package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.ArtistListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.last_fm.TrackListResponse
import com.example.scrutinizing_the_service.v2.data.remote.core.NetworkResult

interface SearchRepository {

    suspend fun searchAlbum(album: String): NetworkResult<AlbumListResponse>

    suspend fun searchArtist(artist: String): NetworkResult<ArtistListResponse>

    suspend fun searchTrack(track: String): NetworkResult<TrackListResponse>

}