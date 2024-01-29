package com.example.data.models.remote.saavn

import com.example.data.models.remote.saavn.Image
import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    val explicitContent: String? = null,
    val firstname: String? = null,
    val id: String? = null,
    val image: List<Image>? = null,
    val language: String? = null,
    val subtitle: String? = null,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
)