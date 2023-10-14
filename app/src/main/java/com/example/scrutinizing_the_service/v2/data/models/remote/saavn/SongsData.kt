package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class SongsData(
    val total: Int,
    val start: Int,
    val results: List<Song>
)