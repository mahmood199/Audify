package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class HomePageResponse(
    val data: Data? = null,
    val message: String? = null,
    val status: String,
)