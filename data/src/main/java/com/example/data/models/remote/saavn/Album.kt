package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val explicitContent: String? = null,
    val id: String,
    val image: List<Image> = emptyList(),
    val language: String? = null,
    val name: String,
    val playCount: String? = null,
    val songCount: String? = null,
    val releaseDate: String? = null,
    val type: String? = null,
    val primaryArtists: List<Artist> = emptyList(),
    val url: String,
    val year: String? = null
) {
    companion object {
        fun default(index: Int) = Album(
            explicitContent = null,
            id = "231432${index}",
            name = "Demo album",
            url = "http://www.testingmcafeesites.com/testcat_wx.html"
        )
    }
}