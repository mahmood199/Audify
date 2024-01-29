package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val explicitContent: String? = null,
    val firstname: String,
    val followerCount: String? = null,
    val id: String,
    val image: List<Image>,
    val lastUpdated: String? = null,
    val songCount: String,
    val subtitle: String,
    val title: String,
    val type: String? = null,
    val url: String? = null,
    val userId: String? = null,
)