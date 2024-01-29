package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class HomePageResponse(
    val data: HomePageData? = null,
    val message: String? = null,
    val status: String,
)