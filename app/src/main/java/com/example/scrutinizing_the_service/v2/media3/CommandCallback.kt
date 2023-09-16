package com.example.scrutinizing_the_service.v2.media3

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.Rating
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class CommandCallback : MediaSession.Callback {

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        val connectionResult = super.onConnect(session, controller)
        val availableSessionCommands = connectionResult.availableSessionCommands.buildUpon()

        return MediaSession.ConnectionResult.accept(
            availableSessionCommands.build(),
            connectionResult.availablePlayerCommands
        )
    }

    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        super.onPostConnect(session, controller)
    }

    override fun onDisconnected(session: MediaSession, controller: MediaSession.ControllerInfo) {
        super.onDisconnected(session, controller)
    }

    override fun onSetRating(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaId: String,
        rating: Rating
    ): ListenableFuture<SessionResult> {
        return super.onSetRating(session, controller, mediaId, rating)
    }

    override fun onSetRating(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        rating: Rating
    ): ListenableFuture<SessionResult> {
        return super.onSetRating(session, controller, rating)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        return super.onCustomCommand(session, controller, customCommand, args)
    }

    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: MutableList<MediaItem>
    ): ListenableFuture<MutableList<MediaItem>> {
        val updatedMediaItems = mediaItems.map { mediaItem ->
            mediaItem.buildUpon()
                .setUri(mediaItem.requestMetadata.mediaUri)
                .build()
        }.toMutableList()
        return Futures.immediateFuture(updatedMediaItems)
    }

}