package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.v2.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.util.px
import com.linc.audiowaveform.infiniteLinearGradient
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AudioPlayerProgressUI(
    progress: Float,
    backGroundColor: Color,
    progressColor: Brush,
    seekToPosition: (Float) -> Unit,
    modifier: Modifier = Modifier
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = LinearEasing),
        label = "Seek bar progress"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {

        val screenWidth = maxWidth.value
        val screenHeight = maxHeight.value

        val context = LocalContext.current

        val maxWidthInPx = remember(maxWidth.value) {
            maxWidth.value.px(context)
        }

        val maxHeightInPx = remember(maxHeight.value) {
            maxHeight.value.px(context)
        }

        val barsCount = remember {
            screenWidth / 2
        }.toInt()

        val random = remember {
            Random.Default
        }

        val heights = remember {
            buildList {
                for (i in 0..barsCount) {
                    add(element = screenHeight * (random.nextFloat() * 0.5f + 0.5f))
                }
            }.toPersistentList()
        }

        val space = remember {
            maxWidth / 25
        }.value

        var startX = remember {
            space
        }

        val path = remember {
            Path().apply {
                heights.forEachIndexed { _, barHeight ->
                    if (startX + 3 * space < maxWidthInPx) {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(
                                        x = startX,
                                        y = (maxHeightInPx - barHeight) * 0.5f
                                    ),
                                    size = Size(width = screenWidth * 0.025f, barHeight)
                                ),
                                radiusX = 10f,
                                radiusY = 10f
                            )
                        )
                        startX += space
                    }
                }
                close()
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            seekToPosition(offset.x / maxWidthInPx)
                        }
                    )
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        seekToPosition(change.position.x / maxWidthInPx)
                    }
                }
                .padding(horizontal = 6.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawRect(
                brush = progressColor,
                topLeft = Offset(0f, canvasHeight * 0.25f),
                size = Size(
                    width = canvasWidth * animatedProgress.coerceAtMost(1f),
                    height = canvasHeight * 0.5f
                )
            )

            clipPath(path = path, clipOp = ClipOp.Difference) {
                drawRoundRect(
                    color = backGroundColor,
                    size = Size(width = canvasWidth, height = canvasHeight),
                    cornerRadius = CornerRadius(screenWidth * 0.1f, screenWidth * 0.1f)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewAudioPlayerProgressUI() {
    ScrutinizingTheServiceTheme {

        var progress by remember {
            mutableFloatStateOf(0f)
        }

        LaunchedEffect(Unit) {
            progress = 0.25f
            delay(500)
            progress = 0.5f
            delay(500)
            progress = 0.75f
            delay(500)
            progress = 1.0f
        }

        val animatedGradientBrush = Brush.infiniteLinearGradient(
            colors = listOf(Color(0xff22c1c3), Color(0xfffdbb2d)),
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            width = 128F
        )


        AudioPlayerProgressUI(
            progress = progress,
            backGroundColor = Color.Blue,
            progressColor = animatedGradientBrush,
            seekToPosition = {
                progress = it
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}