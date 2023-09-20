package com.example.scrutinizing_the_service.v2.data.models.remote

import com.google.gson.annotations.SerializedName

data class OffsetAttribute(
    @SerializedName("num_res")
    val numRes: String,
    @SerializedName("offset")
    val offset: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("perPage")
    val perPage: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("totalPages")
    val totalPages: String,
)