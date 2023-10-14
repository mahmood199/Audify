package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val album: Album,
    val duration: String,
    val explicitContent: String,
    val downloadUrl: List<DownloadDetail> = emptyList(),
    val hasLyrics: String,
    val image: List<Image> = emptyList(),
    val id: String,
    val label: String,
    val language: String,
    val name: String,
    val playCount: String,
    val releaseDate: String? = "",
    val type: String,
    val url: String,
    val year: String,
)