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
) {
    companion object {
        fun default(index: Int): Playlist {
            return Playlist(
                explicitContent = "No",
                firstname = "Dua",
                followerCount = "10000000",
                id = "$index",
                image = listOf(
                    Image(
                        link = "https://example.com/dua_lipa_playlist_cover.jpg",
                        quality = "High"
                    )
                ),
                lastUpdated = "2024-03-02",
                songCount = "20",
                subtitle = "Dua Lipa's Greatest Hits",
                title = "Best of Dua Lipa",
                type = "Artist Playlist",
                url = "https://example.com/dua_lipa_playlist",
                userId = "dua_lipa_fan"
            )
        }
    }
}