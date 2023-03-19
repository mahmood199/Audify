package com.example.scrutinizing_the_service.exo_player.service

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.scrutinizing_the_service.util.PackageAccessValidator

class MusicPlayerService : MediaBrowserServiceCompat() {

    companion object {
        private const val TAG = "MusicPlayerService"
        private const val MY_MEDIA_ROOT_ID = "media_root_id"
        private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    }

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    override fun onCreate() {
        super.onCreate()
        initializeMediaSession()
    }

    private fun initializeMediaSession() {
        mediaSession = MediaSessionCompat(baseContext, TAG).apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            stateBuilder = PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
            setPlaybackState(stateBuilder.build())

            setCallback(MusicSessionCallback())

            setSessionToken(sessionToken)
        }
    }

    /**
     * This method controls access to the service,
     * The onGetRoot() method returns the root node of the content hierarchy.
     *
     *
     * If the method returns null, the connection is refused.
     * To allow clients to connect to your service and browse its media content,
     * onGetRoot() must return a non-null BrowserRoot which is a root ID that represents your content hierarchy.
     *
     *
     * To allow clients to connect to your MediaSession without browsing,
     * onGetRoot() must still return a non-null BrowserRoot,
     * but the root ID should represent an empty content hierarchy.
     */
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return if (PackageAccessValidator.allowBrowsing())
            BrowserRoot(MY_MEDIA_ROOT_ID, null)
        else
            BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }

}