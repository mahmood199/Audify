package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

data class Album(
    val explicitContent: String? = null,
    val id: String,
    val image: List<Image>,
    val language: String? = null,
    val name: String,
    val playCount: String? = null,
    val songCount: String? = null,
    val releaseDate: String? = null,
    val type: String? = null,
    val url: String? = null,
    val year: String? = null
)