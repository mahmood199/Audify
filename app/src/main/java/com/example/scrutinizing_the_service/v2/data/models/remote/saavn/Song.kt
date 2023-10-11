package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val album: Album,
    val duration: String,
    val explicitContent: String,
    val featuredArtists: List<Artist> = emptyList(),
    val id: String,
    val image: List<Image> = emptyList(),
    val label: String,
    val language: String,
    val name: String,
    val playCount: String,
    val releaseDate: String,
    val type: String,
    val url: String,
    val year: String,
)