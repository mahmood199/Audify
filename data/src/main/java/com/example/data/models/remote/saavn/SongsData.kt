package com.example.data.models.remote.saavn

import com.example.data.models.remote.saavn.Song
import kotlinx.serialization.Serializable

@Serializable
data class SongsData(
    val total: Int,
    val start: Int,
    val results: List<Song>
)