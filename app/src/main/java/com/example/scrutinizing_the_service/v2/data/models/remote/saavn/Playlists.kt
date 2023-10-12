package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Playlists(
    val explicitContent: String? = null,
    val firstname: String? = null,
    val followerCount: String? = null,
    val id: String? = null,
    val image: List<Image>? = null,
    val lastUpdated: String? = null,
    val songCount: String? = null,
    val subtitle: String? = null,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
    val userId: String? = null,
)