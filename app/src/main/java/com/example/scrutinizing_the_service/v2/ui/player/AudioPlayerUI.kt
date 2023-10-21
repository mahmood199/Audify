package com.example.scrutinizing_the_service.v2.ui.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.compose_utils.SaveableLaunchedEffect
import com.example.scrutinizing_the_service.v2.ui.common.AppBar
import com.example.scrutinizing_the_service.v2.ui.common.AudioPlayerProgressUI
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.linc.audiowaveform.infiniteLinearGradient

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AudioPlayerUI(
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AudioPlayerViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val systemUiController = rememberSystemUiController()

    SaveableLaunchedEffect(Unit) {
        // Match the color of bottom player and navigation bar for better UX
        systemUiController.setNavigationBarColor(Color.Cyan)
    }

    Scaffold(
        topBar = {
            AppBar(
                imageVector = Icons.Default.ArrowBack,
                title = "Music Player UI",
                backPressAction = backPress,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .shadow(12.dp)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
                    .background(Color.Cyan)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                AudioPlayerState(
                    state = state,
                    seekToPosition = {
                        viewModel.sendUIEvent(PlayerUiEvent.UpdateProgress(it))
                    }
                )

                PlayerActions(
                    isPlaying = state.isPlaying,
                    skipToNext = {
                        viewModel.sendUIEvent(PlayerUiEvent.PlayNextItem)
                    },
                    pausePlay = {
                        viewModel.sendUIEvent(PlayerUiEvent.PlayPause)
                    },
                    skipToPrevious = {
                        viewModel.sendUIEvent(PlayerUiEvent.PlayPreviousItem)
                    }
                )

                ClosePlayerBottomArc(
                    backPress = backPress,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
        modifier = modifier
            .background(Color.Green)
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .background(Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = "Hey there",
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .basicMarquee(300)
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10))
                    .background(Color.DarkGray)
            ) {

            }

            Text(
                text = "Hey there",
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .basicMarquee(300)
                    .padding(12.dp),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
fun AudioPlayerState(
    state: AudioPlayerViewState,
    seekToPosition: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val animatedGradientBrush = Brush.infiniteLinearGradient(
            colors = listOf(
                Color(0xff22c1c3),
                Color(0xfffdbb2d),
                Color.Red,
                Color.Magenta
            ),
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            width = 128F
        )

        AudioPlayerProgressUI(
            progress = state.progress,
            backGroundColor = Color.Blue,
            progressColor = animatedGradientBrush,
            seekToPosition = {
                seekToPosition(it)
            },
            modifier = Modifier.fillMaxHeight(0.1f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = state.progressString,
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color.Black
            )
            Text(
                text = state.duration,
                modifier = Modifier.align(Alignment.CenterEnd),
                color = Color.Black
            )
        }
    }
}

@Composable
fun PlayerActions(
    isPlaying: Boolean,
    skipToPrevious: () -> Unit,
    skipToNext: () -> Unit,
    pausePlay: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isAddedToFavourite by remember {
        mutableStateOf(false)
    }

    var repeatMode by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                repeatMode = repeatMode.not()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (repeatMode)
                        R.drawable.ic_repeat_one
                    else
                        R.drawable.ic_repeat_playlist
                ),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .padding(2.dp)
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_skip_previous),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .size(40.dp)
                .clickable {
                    skipToPrevious()
                }
                .border(width = 2.dp, color = Color.Black, CircleShape)
                .padding(2.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(
                if (isPlaying)
                    R.drawable.ic_pause
                else
                    R.drawable.ic_play
            ),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .size(64.dp)
                .clickable {
                    pausePlay()
                }
                .border(width = 2.dp, color = Color.Black, CircleShape)
                .padding(8.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_skip_next),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .size(40.dp)
                .clickable {
                    skipToNext()
                }
                .border(width = 2.dp, color = Color.Black, CircleShape)
                .padding(2.dp)
        )
        IconButton(
            onClick = {
                isAddedToFavourite = !isAddedToFavourite
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (isAddedToFavourite)
                        R.drawable.ic_favorite_filled
                    else
                        R.drawable.ic_favorite
                ),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun ClosePlayerBottomArc(
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .height(40.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    backPress()
                },
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawArc(
                Color.Blue,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(canvasWidth, 3 * canvasHeight)
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surface,
            modifier = Modifier.size(32.dp)
        )
    }
}


@Preview
@Composable
fun AudioPlayerUIPreview() {
    AudioPlayerUI(backPress = {})
}