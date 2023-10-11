package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

data class Data(
    val albums: List<Album>? = null,
    val charts: List<Chart>? = null,
    val playlists: List<Playlists>? = null,
    val trending: Trending? = null,
)