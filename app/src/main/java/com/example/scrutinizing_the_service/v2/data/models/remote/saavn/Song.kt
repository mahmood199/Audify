package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

data class Song(
    val album: Album? = null,
    val duration: String? = null,
    val explicitContent: String? = null,
    val featuredArtists: List<Artist>? = null,
    val id: String? = null,
    val image: List<Image>? = null,
    val label: String? = null,
    val language: String? = null,
    val name: String? = null,
    val playCount: String? = null,
    val primaryArtists: List<PrimaryArtist>? = null,
    val releaseDate: String? = null,
    val type: String? = null,
    val url: String? = null,
    val year: String? = null,
)