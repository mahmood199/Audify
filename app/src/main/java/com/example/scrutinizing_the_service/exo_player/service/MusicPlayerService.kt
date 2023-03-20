package com.example.scrutinizing_the_service.exo_player.service

import android.media.session.MediaSession
import android.media.session.MediaSession.Token
import android.media.session.PlaybackState
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.RequiresApi
import androidx.media.MediaBrowserServiceCompat
import com.example.scrutinizing_the_service.util.PackageAccessValidator

@RequiresApi(Build.VERSION_CODES.O)
class MusicPlayerService : MediaBrowserServiceCompat() {

    companion object {
        private const val TAG = "MusicPlayerService"
        private const val MY_MEDIA_ROOT_ID = "media_root_id"
        private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    }

    private lateinit var mediaSession: MediaSession
    private lateinit var stateBuilder: PlaybackState.Builder

    private val notificationBuilder by lazy {
        MusicNotificationBuilder(
            this,
            mediaSession
        )
    }

    override fun onCreate() {
        super.onCreate()
        initializeMediaSession()
    }

    private fun initializeMediaSession() {
        mediaSession = MediaSession(baseContext, TAG).apply {
            setFlags(
                MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            stateBuilder = PlaybackState.Builder().setActions(
                PlaybackState.ACTION_PLAY or
                        PlaybackState.ACTION_PLAY_PAUSE
            )
            setPlaybackState(stateBuilder.build())

            setCallback(MusicSessionCallback())

            setSessionToken(MediaSessionCompat.Token.fromToken("1234"))
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
        if(MY_EMPTY_MEDIA_ROOT_ID == parentId) {
            result.sendResult(null)
            return
        } else {
            val mediaItems = mutableListOf<MediaBrowserCompat.MediaItem>()
            if(MY_MEDIA_ROOT_ID == parentId) {
                /**
                 * Build the MediaItem objects for the top level,
                 * and put them in the mediaItems list...
                 */
            } else {
                /**
                 * Examine the passed parentMediaId to see which submenu we're at,
                 * and put the children of that menu in the mediaItems list...
                 */
            }
            result.sendResult(mediaItems)
        }
    }

}