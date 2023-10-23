package com.example.scrutinizing_the_service.v2.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.example.scrutinizing_the_service.v2.media3.CommandCallback
import com.example.scrutinizing_the_service.v2.media3.PlayerDelegate
import com.example.scrutinizing_the_service.v2.media3.PlayerDelegateImpl
import com.example.scrutinizing_the_service.v2.media3.SimpleMediaNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@UnstableApi
object Media3Module {

    @Singleton
    @Provides
    fun provideAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    @Singleton
    @Provides
    fun provideExoPlayerBuilder(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes
    ): ExoPlayer.Builder {
        return ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(SEEK_BACK_IN_MILLIS)
            .setSeekForwardIncrementMs(SEEK_FORWARD_IN_MILLIS)
            .setAudioAttributes(
                audioAttributes,
                true
            )
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
    }

    @Singleton
    @Provides
    fun providePlayer(
        exoPlayerBuilder: ExoPlayer.Builder
    ): Player {
        return exoPlayerBuilder.build()
    }

    @Singleton
    @Provides
    fun provideCommandCallback(): CommandCallback {
        return CommandCallback()
    }

    @Singleton
    @Provides
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: Player
    ): MediaSession {
        return MediaSession.Builder(context, player)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        player: Player,
    ): SimpleMediaNotificationManager {
        return SimpleMediaNotificationManager(
            context = context,
            player = player
        )
    }

/*
    @Singleton
    @Provides
    fun providePlayerDelegate(
        player: Player
    ): PlayerDelegate = PlayerDelegateImpl(player)
*/

    private const val SEEK_BACK_IN_MILLIS = 10000L
    private const val SEEK_FORWARD_IN_MILLIS = 10000L

}
