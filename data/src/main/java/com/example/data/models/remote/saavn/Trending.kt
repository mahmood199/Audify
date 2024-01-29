package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Trending(
    val albums: List<Album>? = null,
    val songs: List<Song>? = null,
)