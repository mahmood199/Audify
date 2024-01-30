package com.example.audify.v2.media3

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerDelegateImpl @Inject constructor(
    private val player: Player,
    private val playerDelegate: PlayerDelegate
) : PlayerDelegate by playerDelegate {

    private val _audioState: MutableStateFlow<PlayerState> =
        MutableStateFlow(PlayerState.Initial)
    val audioState: StateFlow<PlayerState> = _audioState.asStateFlow()

    private var job: Job? = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        player.addListener(this)
    }

    override fun addItems(mediaItems: List<MediaItem>) {
        if (player.mediaItemCount == 0) {
            player.playlistMetadata
            player.setMediaItems(mediaItems)
            player.prepare()
        }
    }

    override suspend fun onPlayerEvents(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.PlayPause -> playOrPause()
            PlayerEvent.Rewind -> player.seekBack()
            PlayerEvent.FastForward -> player.seekForward()
            is PlayerEvent.PlaySongAt -> {
                if (player.currentMediaItemIndex == playerEvent.index) {
                    playOrPause()
                } else {
                    with(player) {
                        seekTo(playerEvent.index, 0)
                        playWhenReady = true
                        _audioState.value = PlayerState.Playing(
                            isPlaying = true,
                        )
                    }
                    startProgressUpdate()
                }
            }

            is PlayerEvent.UpdateProgress -> {
                player.seekTo(
                    (player.duration * playerEvent.newProgress).toLong()
                )
            }

            PlayerEvent.PlayNextItem -> {
                with(player) {
                    seekTo(player.nextMediaItemIndex, 0)
                    prepare()
                    play()
                }
            }

            PlayerEvent.PlayPreviousItem -> {
                with(player) {
                    seekTo(player.previousMediaItemIndex, 0)
                    prepare()
                    play()
                }
            }
        }
    }

    override suspend fun playOrPause() {
        if (player.isPlaying) {
            player.pause()
            stopProgressUpdate()
        } else {
            player.play()
            _audioState.value = PlayerState.Playing(
                isPlaying = true,
            )
            startProgressUpdate()
        }
    }

    override suspend fun startProgressUpdate() {
        job.run {
            while (true) {
                delay(500)
                _audioState.value = PlayerState.Progress(
                    progress = player.currentPosition,
                    duration = player.duration,
                    mediaItem = player.currentMediaItem
                )
            }
        }

    }

    override fun stopProgressUpdate() {
        job?.cancel()
        coroutineScope.cancel()
        _audioState.value = PlayerState.Playing(
            isPlaying = false,
        )
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        nestedOnIsPlayingChanged(isPlaying)
    }

    override fun nestedOnIsPlayingChanged(isPlaying: Boolean) {
        _audioState.value = PlayerState.Playing(
            isPlaying = isPlaying,
        )
        _audioState.value = PlayerState.CurrentPlaying(
            mediaItem = player.currentMediaItem,
            duration = player.duration
        )
        if (isPlaying) {
            coroutineScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        nestedOnPlaybackStateChanged(playbackState)
    }

    override fun nestedOnPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _audioState.value =
                PlayerState.Buffering(
                    progress = player.currentPosition,
                    duration = player.duration
                )

            ExoPlayer.STATE_READY -> _audioState.value =
                PlayerState.Ready(player.duration)
        }
    }

}