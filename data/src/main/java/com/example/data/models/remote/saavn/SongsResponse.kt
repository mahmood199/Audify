package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class SongsResponse(
    val data: SongsData,
    val message: String? = null,
    val status: String
)