package com.example.scrutinizing_the_service.v2.data.models.response

import com.google.gson.annotations.SerializedName

data class ArtistListResponse(
    @SerializedName("topartists")
    val topArtists: TopArtists
)