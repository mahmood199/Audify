package com.example.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class ArtistListResponse(
    @SerializedName("results")
    val artistResults: ArtistResults
)