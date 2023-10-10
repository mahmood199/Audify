package com.example.scrutinizing_the_service.v2.network

import android.util.Log
import com.example.scrutinizing_the_service.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SaavnClient {

    private val client by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    allowStructuredMapKeys = true
                })
            }

            install(ContentEncoding) {
                brotli()
            }

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("Header:", response.headers.toString())
                    Log.d("Url:", response.request.url.toString())
                    Log.d("Method", response.request.method.value)
                    Log.d("Request:", response.request.content.toString())
                    Log.d("HTTP status:", "${response.status.value}")
                    Log.d("Response:", response.bodyAsText())
                }
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            defaultRequest {
                url(host = BASE_URL) {
                    headers.append(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                }
            }
        }
    }

    operator fun invoke() = client


    companion object {
        private const val TIME_OUT = 60_000
        val BASE_URL = BuildConfig.SAAVN_BASE_URL
    }

}