package com.example.data.repo.contracts

import com.example.data.models.remote.last_fm.AlbumListResponse
import com.example.data.models.remote.last_fm.ArtistListResponse
import com.example.data.models.remote.last_fm.TrackListResponse
import com.example.data.remote.core.NetworkResult

interface SearchRepository {

    suspend fun searchAlbum(album: String): NetworkResult<AlbumListResponse>

    suspend fun searchArtist(artist: String): NetworkResult<ArtistListResponse>

    suspend fun searchTrack(track: String): NetworkResult<TrackListResponse>

}