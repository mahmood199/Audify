package com.example.data.models.remote.saavn


data class ArtistDetailResponse(
    val data: ArtistData,
    val message: Any,
    val status: String
)