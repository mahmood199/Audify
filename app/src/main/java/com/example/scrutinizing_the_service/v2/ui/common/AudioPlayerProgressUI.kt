package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

@Composable
fun AudioPlayerProgressUI(
    progress: Float,
    widgetWidth: Dp,
    widgetHeight: Dp,
    modifier: Modifier = Modifier
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = LinearEasing),
        label = "Seek bar progress"
    )

    Box(
        modifier = modifier
            .width(widgetWidth)
            .height(widgetHeight)
    ) {

        val screenWidth = widgetWidth.value
        val screenHeight = widgetHeight.value

        val barsCount = remember {
            screenWidth / 2
        }.toInt()

        val random = remember {
            Random.Default
        }

        val heights = remember {
            buildList {
                for (i in 0..barsCount) {
                    add(element = screenHeight * (random.nextFloat() * 0.5f + 0.1f))
                }
            }.toPersistentList()
        }

        val space = remember {
            widgetWidth / 35
        }.value

        var startX = remember {
            0f
        }

        val path = remember {
            Path().apply {
                heights.forEachIndexed { index, barHeight ->
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                offset = Offset(
                                    x = startX,
                                    y = screenHeight * 1f - barHeight * 0.5f
                                ),
                                size = Size(width = screenWidth * 0.01f, barHeight)
                            ),
                            radiusX = 10f,
                            radiusY = 10f
                        )
                    )
                    startX += space
                }
                close()
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawRect(
                color = Color.Red,
                topLeft = Offset(0f, canvasHeight * 0.2f),
                size = Size(
                    width = canvasWidth * animatedProgress,
                    height = canvasHeight * 0.5f
                )
            )

            clipPath(path = path, clipOp = ClipOp.Difference) {
                drawRoundRect(
                    color = Color.White,
                    size = Size(width = canvasWidth, height = canvasHeight * 0.75f),
                    cornerRadius = CornerRadius(screenWidth * 0.05f, screenWidth * 0.05f)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewAudioPlayerProgressUI() {
    ScrutinizingTheServiceTheme {
        val height = LocalConfiguration.current.screenHeightDp / 2
        val width = LocalConfiguration.current.screenWidthDp / 1

        AudioPlayerProgressUI(
            progress = 0.3f,
            widgetWidth = width.dp,
            widgetHeight = height.dp,
            modifier = Modifier
        )
    }
}