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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.ui.common.AppBar
import com.example.scrutinizing_the_service.v2.ui.common.AudioPlayerProgressUI
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.infiniteLinearGradient
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AudioPlayerUI(
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AudioPlayerViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    val currentPlayingTime by remember {
        mutableLongStateOf(30L)
    }

    val duration by remember {
        mutableLongStateOf(100L)
    }

    Scaffold(
        topBar = {
            AppBar(
                imageVector = Icons.Default.ArrowBack,
                title = "Music Player UI",
                backPressAction = backPress,
                modifier = Modifier.shadow(12.dp)
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
                var waveformProgress by remember(Unit) { mutableFloatStateOf(0F) }

                val animatedGradientBrush = Brush.infiniteLinearGradient(
                    colors = listOf(Color(0xff22c1c3), Color(0xfffdbb2d)),
                    animation = tween(durationMillis = 6000, easing = LinearEasing),
                    width = 128F
                )

                val configuration = LocalConfiguration.current
                val numberOfWaves = remember(configuration.screenWidthDp) {
                    (configuration.screenWidthDp / 5f).roundToInt()
                }

                AudioWaveform(
                    progress = waveformProgress,
                    progressBrush = animatedGradientBrush,
                    amplitudes = List(numberOfWaves) {
                        Random.nextInt(from = 1, until = 100)
                    },
                    onProgressChange = { waveformProgress = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val width = LocalConfiguration.current.screenWidthDp
        val height = LocalConfiguration.current.screenHeightDp / 10

        AudioPlayerProgressUI(
            progress = state.progress,
            widgetWidth = width.dp,
            widgetHeight = height.dp,
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
                text = "${state.duration}",
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

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_repeat_playlist),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .size(40.dp)
                .padding(2.dp)
        )
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
                .clickable {
                    isAddedToFavourite = !isAddedToFavourite
                }
                .padding(2.dp)
        )
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

@Composable
fun SimpleArc() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(0.5f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topEndPercent = 100, topStartPercent = 100))
                .background(Color.Red)
        ) {

        }
    }
}

@Composable
fun CanvasArc(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 600.dp)
            .border(color = Color.Magenta, width = 2.dp)
    ) {
        drawArc(
            color = Color.Cyan,
            startAngle = -45f,
            sweepAngle = -90f,
            useCenter = false,
            size = Size(size.width, size.height)
        )
    }
}


@Preview
@Composable
fun AudioPlayerUIPreview() {
    AudioPlayerUI(backPress = {})
}