package com.example.scrutinizing_the_service.v2.data.models.response

import com.example.scrutinizing_the_service.v2.data.models.response.Artist
import com.example.scrutinizing_the_service.v2.data.models.response.OffsetAttribute
import com.google.gson.annotations.SerializedName

data class TopArtists(
    @SerializedName("@attr")
    val attr: OffsetAttribute,
    @SerializedName("artist")
    val artist: List<Artist>
)