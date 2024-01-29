package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val link: String,
    val quality: String,
)