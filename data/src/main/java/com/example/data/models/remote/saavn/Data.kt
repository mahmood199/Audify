package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val albums: List<Album>? = null,
    val charts: List<Chart>? = null,
    val playlists: List<Playlists>? = null,
    val trending: Trending? = null,
)