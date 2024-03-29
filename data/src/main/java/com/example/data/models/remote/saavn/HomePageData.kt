package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class HomePageData(
    val albums: List<Album>? = null,
    val charts: List<Chart>? = null,
    val playlists: List<Playlist>? = null,
    val trending: Trending? = null,
)