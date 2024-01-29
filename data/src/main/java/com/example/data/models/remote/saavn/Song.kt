package com.example.data.models.remote.saavn

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
) {
    companion object {
        fun default(): Song {
            return Song(
                album = Album.default(),
                duration = "03:20",
                explicitContent = "No",
                hasLyrics = "No",
                id = "231231",
                downloadUrl = emptyList(),
                image = emptyList(),
                label = "Some label",
                language = "Some language",
                name = "Some Name",
                playCount = "231432",
                releaseDate = null,
                type = "Some type",
                url = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                year = "2023"
            )
        }
    }
}