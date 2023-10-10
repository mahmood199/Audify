package com.example.scrutinizing_the_service.v2.network

import com.example.scrutinizing_the_service.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.gson.GsonConverter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        val BASE_URL = BuildConfig.LAST_FM_BASE_URL
    }

    @Singleton
    @Provides
    fun provideSaavnClient(): SaavnClient {
        return SaavnClient()
    }

    @Singleton
    @Provides
    fun provideLastFmClient(): LastFmClient {
        return LastFmClient()
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
