package com.example.data.models.remote.saavn

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val link: String,
    val quality: String,
) {
    companion object {
        fun default(): Image {
            return Image(
                link = "https://placebear.com/g/200/200",
                quality = "200"
            )
        }
    }
}