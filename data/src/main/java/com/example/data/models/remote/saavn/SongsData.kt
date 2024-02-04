package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class SongsData(
    val total: Int,
    val start: Int,
    val results: List<Song>
)