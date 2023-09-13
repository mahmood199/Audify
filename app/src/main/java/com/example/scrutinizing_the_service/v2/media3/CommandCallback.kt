package com.example.scrutinizing_the_service.v2.media3

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Rating
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionCommands
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.ListenableFuture
import com.example.scrutinizing_the_service.R
import com.google.common.util.concurrent.Futures

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
        val playPause = CommandButton.Builder()
            .setDisplayName(if (session.player.isPlaying) "Play" else "Pause")
            .setIconResId(
                if (session.player.isPlaying) R.drawable.ic_play
                else R.drawable.ic_pause
            )
            .setPlayerCommand(Player.COMMAND_PLAY_PAUSE)
            .build()

        val forwardSeek = CommandButton.Builder()
            .setDisplayName("Fast Forward")
            .setIconResId(R.drawable.ic_fast_forward)
            .setPlayerCommand(Player.COMMAND_SEEK_FORWARD)
            .build()

        val backwardSeek = CommandButton.Builder()
            .setDisplayName("Rewind")
            .setIconResId(R.drawable.ic_rewind)
            .setPlayerCommand(Player.COMMAND_SEEK_BACK)
            .build()

        session.setCustomLayout(controller, listOf(playPause, forwardSeek, backwardSeek))

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