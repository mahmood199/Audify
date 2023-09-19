package com.example.scrutinizing_the_service.v2.network

import android.util.Log
import com.example.scrutinizing_the_service.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private val TIME_OUT = 60_000
        private const val API_KEY = "9f0d1f4e45452f005252775976e4274c"
        private val SHARED_KEY = "8e9162fd6f5930584a1adfa06507c3bb"
        val BASE_URL = "https://ws.audioscrobbler.com/"
    }

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    allowStructuredMapKeys = true
                })
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

            /*
                        Logging {
                            logger = Logger.DEFAULT
                            level = LogLevel.ALL
                        }
            */

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }


        }.apply {
            sendPipeline.intercept(HttpSendPipeline.State) {
                context.headers.append("AppVersion", BuildConfig.VERSION_NAME)
                context.parameter("api_key", API_KEY)
                context.parameter("format", "json")
            }
        }
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverter(gson: Gson): GsonConverter {
        return GsonConverter(gson)
    }

}
