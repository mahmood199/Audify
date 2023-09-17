package com.example.scrutinizing_the_service.v2.data.models.response

import com.example.scrutinizing_the_service.v2.data.models.response.OffsetAttribute
import com.example.scrutinizing_the_service.v2.data.models.response.Track
import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("@attr")
    val offsetAttribute: OffsetAttribute,
    @SerializedName("track")
    val track: List<Track>
)