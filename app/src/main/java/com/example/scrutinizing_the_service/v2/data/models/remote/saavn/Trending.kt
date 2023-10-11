package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

data class Trending(
    val albums: List<Album>? = null,
    val songs: List<Song>? = null,
)