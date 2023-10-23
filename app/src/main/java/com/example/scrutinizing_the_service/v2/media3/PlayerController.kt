package com.example.scrutinizing_the_service.v2.media3

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListUiEvent
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
class PlayerController @Inject constructor(
    private val player: Player
) : Player.Listener {

    private val _audioState: MutableStateFlow<PlayerState> =
        MutableStateFlow(PlayerState.Initial)
    val audioState: StateFlow<PlayerState> = _audioState.asStateFlow()

    private var job: Job? = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        player.addListener(this)
    }

    suspend fun sendMediaEvent(action: MediaPlayerAction) {
        when (action) {

            is MediaPlayerAction.UpdateProgress -> {
                player.seekTo((player.duration * action.newProgress).toLong())
            }

            MediaPlayerAction.PlayPause -> {
                playOrPause()
            }

            MediaPlayerAction.Rewind -> {
                player.seekBack()
            }

            MediaPlayerAction.FastForward -> {
                player.seekForward()
            }

            MediaPlayerAction.PlayNextItem -> {
                with(player) {
                    seekTo(player.nextMediaItemIndex, 0)
                    prepare()
                    play()
                }
            }

            MediaPlayerAction.PlayPreviousItem -> {
                with(player) {
                    seekTo(player.previousMediaItemIndex, 0)
                    prepare()
                    play()
                }
            }

            is MediaPlayerAction.PlaySongAt -> {
                if (player.currentMediaItemIndex == action.index) {
                    playOrPause()
                } else {
                    with(player) {
                        seekTo(action.index, 0)
                        playWhenReady = true
                        _audioState.value = PlayerState.Playing(
                            isPlaying = true,
                        )
                    }
                    startProgressUpdate()
                }
            }

            is MediaPlayerAction.SetMediaItem -> {
                setMediaItem(action.mediaItem)
            }

        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        with(player) {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    fun addItems(mediaItems: List<MediaItem>) {
        if (player.mediaItemCount == 0) {
            player.playlistMetadata
            player.setMediaItems(mediaItems)
            player.prepare()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
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

    override fun onIsPlayingChanged(isPlaying: Boolean) {
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

    private suspend fun playOrPause() {
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

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _audioState.value = PlayerState.Progress(
                progress = player.currentPosition,
                duration = player.duration,
                mediaItem = player.currentMediaItem
            )
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        coroutineScope.cancel()
        _audioState.value = PlayerState.Playing(
            isPlaying = false,
        )
    }

}