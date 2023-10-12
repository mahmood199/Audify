package com.example.scrutinizing_the_service.v2.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class ArtistMatchesList(
    @SerializedName("artist")
    val artists: List<Artist>
)