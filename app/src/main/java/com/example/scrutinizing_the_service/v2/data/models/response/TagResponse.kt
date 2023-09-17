package com.example.scrutinizing_the_service.v2.data.models.response

import com.example.scrutinizing_the_service.v2.data.models.response.Tag
import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("tag")
    val tag: Tag,
)
