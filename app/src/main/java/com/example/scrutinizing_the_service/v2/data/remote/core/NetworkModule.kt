package com.example.scrutinizing_the_service.v2.data.remote.core

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.scrutinizing_the_service.BuildConfig
import com.google.common.io.Files.append
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.parameter
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.Duration
import javax.inject.Singleton
import javax.net.SocketFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        val BASE_URL = BuildConfig.LAST_FM_BASE_URL
        const val TIME_OUT_IN_MILLIS = 30_000L
        const val TIME_OUT_IN_SECONDS = 30_000
    }

    @Provides
    @Singleton
    @SaavnClient
    fun provideSaavnClient(
        @ApplicationContext context: Context
    ) = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
                allowStructuredMapKeys = true
            })
        }

        engine {
            config {
                connectTimeout(Duration.ofMillis(TIME_OUT_IN_MILLIS))
                socketFactory(SocketFactory.getDefault().apply {
                    callTimeout(Duration.ofMillis(TIME_OUT_IN_MILLIS))
                })
                addInterceptor(ChuckerInterceptor(context))
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    @Provides
    @Singleton
    @LastFmClient
    fun provideLastFm() = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                allowStructuredMapKeys = true
            })
        }

        engine {
            connectTimeout = TIME_OUT_IN_SECONDS
            socketTimeout = TIME_OUT_IN_SECONDS
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        defaultRequest {
            url(host = BuildConfig.LAST_FM_BASE_URL) {
                headers.append(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            }
        }

    }.apply {
        sendPipeline.intercept(HttpSendPipeline.State) {
            context.headers.append("AppVersion", BuildConfig.VERSION_NAME)
            context.parameter("api_key", BuildConfig.LAST_FM_API_KEY)
            context.parameter("format", "json")
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
