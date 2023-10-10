package com.example.scrutinizing_the_service.v2.network

import io.ktor.client.plugins.compression.ContentEncoding


fun ContentEncoding.Config.brotli(quality: Float? = null) {
    customEncoder(BrotliEncoder, quality)
}
