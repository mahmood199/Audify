package com.example.scrutinizing_the_service.v2.data.models.response

import com.example.scrutinizing_the_service.v2.data.models.response.OffsetAttribute
import com.example.scrutinizing_the_service.v2.data.models.response.Tag
import com.google.gson.annotations.SerializedName

data class TopTags(
    @SerializedName("@attr")
    val offsetAttribute: OffsetAttribute,
    @SerializedName("tag")
    val tags: List<Tag>
)