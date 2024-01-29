package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class DownloadDetail(
    val link: String,
    val quality: String,
)