package com.example.scrutinizing_the_service.v2.data.remote.last_fm

import com.example.scrutinizing_the_service.v2.data.models.remote.AlbumListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.ArtistListResponse
import com.example.scrutinizing_the_service.v2.data.models.remote.TrackListResponse
import com.example.scrutinizing_the_service.v2.network.LastFmClient
import com.example.scrutinizing_the_service.v2.network.NetworkModule
import com.example.scrutinizing_the_service.v2.network.NetworkResult
import com.example.scrutinizing_the_service.v2.network.ResponseProcessor
import com.google.gson.Gson
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val httpClient: LastFmClient,
    private val responseProcessor: ResponseProcessor,
    private val gson: Gson
) {

    suspend fun searchForAlbum(album: String): NetworkResult<AlbumListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "album.search")
            parameter("album", album)
        }
        val result = responseProcessor.getResultFromResponse<AlbumListResponse>(gson, response)
        return result
    }

    suspend fun searchForArtist(artist: String): NetworkResult<ArtistListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "artist.search")
            parameter("artist", artist)
        }
        val result = responseProcessor.getResultFromResponse<ArtistListResponse>(gson, response)
        return result
    }

    suspend fun searchForTrack(track: String): NetworkResult<TrackListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "track.search")
            parameter("track", track)
        }
        val result = responseProcessor.getResultFromResponse<TrackListResponse>(gson, response)
        return result
    }

    suspend fun searchForAlbum(
        album: String,
        nextPageNumber: Int
    ): NetworkResult<AlbumListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "album.search")
            parameter("album", album)
            parameter("page", nextPageNumber)
        }
        val result = responseProcessor.getResultFromResponse<AlbumListResponse>(gson, response)
        return result
    }

    suspend fun searchForArtist(
        album: String,
        nextPageNumber: Int
    ): NetworkResult<ArtistListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "artist.search")
            parameter("artist", album)
            parameter("page", nextPageNumber)
        }
        val result = responseProcessor.getResultFromResponse<ArtistListResponse>(gson, response)
        return result
    }

    suspend fun searchForTrack(
        track: String,
        nextPageNumber: Int
    ): NetworkResult<TrackListResponse> {
        val response = httpClient().get(NetworkModule.BASE_URL) {
            parameter("method", "track.search")
            parameter("track", track)
            parameter("page", nextPageNumber)
        }
        val result = responseProcessor.getResultFromResponse<TrackListResponse>(gson, response)
        return result
    }


}