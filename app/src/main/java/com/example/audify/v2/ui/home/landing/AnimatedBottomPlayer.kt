package com.example.audify.v2.ui.home.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.audify.v2.media3.MediaPlayerAction
import com.example.audify.v2.ui.catalog.MusicListUiEvent
import com.example.audify.v2.ui.common.BottomPlayer

@Composable
fun AnimatedBottomPlayer(
    state: LandingPageViewState,
    isShown: Boolean,
    sendMediaAction: (MediaPlayerAction) -> Unit,
    sendUiEvent: (MusicListUiEvent) -> Unit,
    navigateToPlayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isShown,
        enter = slideIn(initialOffset = { IntOffset(0, 100) }),
        exit = slideOut(targetOffset = { IntOffset(0, 200) }),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            BottomPlayer(
                progress = state.progress,
                songName = state.currentSong?.name ?: "Error",
                artist = state.currentSong?.artist ?: "Error",
                isPlaying = state.isPlaying,
                onPlayPauseClicked = {
                    sendMediaAction(MediaPlayerAction.PlayPause)
                },
                onRewindClicked = {
                    sendMediaAction(MediaPlayerAction.Rewind)
                },
                onFastForwardClicked = {
                    sendMediaAction(MediaPlayerAction.FastForward)
                },
                onPlayPreviousClicked = {
                    sendMediaAction(MediaPlayerAction.PlayPreviousItem)
                },
                onPlayNextClicked = {
                    sendMediaAction(MediaPlayerAction.PlayNextItem)
                },
                navigateToPlayer = {
                    navigateToPlayer()
                },
                seekToPosition = {
                    sendMediaAction(MediaPlayerAction.UpdateProgress(it))
                    sendUiEvent(MusicListUiEvent.UpdateProgress(it))
                },
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    }
}
