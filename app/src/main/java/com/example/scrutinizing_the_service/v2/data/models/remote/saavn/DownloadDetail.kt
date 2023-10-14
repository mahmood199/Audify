package com.example.scrutinizing_the_service.v2.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class DownloadDetail(
    val link: String,
    val quality: String,
)