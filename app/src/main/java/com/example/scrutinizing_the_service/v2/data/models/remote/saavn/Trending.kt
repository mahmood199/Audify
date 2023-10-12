package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Trending(
    val albums: List<Album>? = null,
    val songs: List<Song>? = null,
)