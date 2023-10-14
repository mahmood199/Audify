package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class SongsResponse(
    val data: SongsData,
    val message: String? = null,
    val status: String
)