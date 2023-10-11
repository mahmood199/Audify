package com.example.scrutinizing_the_service.v2.data.models.remote.last_fm

import com.google.gson.annotations.SerializedName

data class TopTags(
    @SerializedName("@attr")
    val offsetAttribute: OffsetAttribute,
    @SerializedName("tag")
    val tags: List<Tag>
)