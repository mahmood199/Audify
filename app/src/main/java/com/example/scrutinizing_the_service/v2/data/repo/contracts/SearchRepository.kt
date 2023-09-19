package com.example.scrutinizing_the_service.v2.data.repo.contracts

import com.example.scrutinizing_the_service.v2.data.models.response.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.response.ArtistListResponse
import com.example.scrutinizing_the_service.v2.data.models.response.TrackListResponse
import com.example.scrutinizing_the_service.v2.network.NetworkResult

interface SearchRepository {

    suspend fun searchAlbum(album: String): NetworkResult<AlbumListResponse>

    suspend fun searchArtist(artist: String): NetworkResult<ArtistListResponse>

    suspend fun searchTrack(track: String): NetworkResult<TrackListResponse>

}