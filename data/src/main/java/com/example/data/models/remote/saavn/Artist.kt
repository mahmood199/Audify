package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val role: String,
    val type: String,
    val url: String
)