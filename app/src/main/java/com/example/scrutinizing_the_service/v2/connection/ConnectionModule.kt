package com.example.scrutinizing_the_service.v2.connection

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConnectionModule {

    @Provides
    @Singleton
    fun providesConnectivityManager(
        @ApplicationContext context: Context,
    ): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun providesTelephonyService(
        @ApplicationContext context: Context,
    ): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

}