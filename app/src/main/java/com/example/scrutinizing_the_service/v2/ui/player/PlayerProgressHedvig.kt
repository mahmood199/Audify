package com.example.scrutinizing_the_service.v2.ui.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.google.android.material.math.MathUtils.lerp
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.random.Random

private const val waveWidthPercentOfSpaceAvailable = 0.5f

@Composable
internal fun FakeAudioWaves(
    progressPercentage: ProgressPercentage,
    playedColor: Color,
    notPlayedColor: Color,
    waveInteraction: WaveInteraction,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier) {
        val updatedWaveInteraction by rememberUpdatedState(waveInteraction)
        val numberOfWaves = remember(maxWidth) {
            (maxWidth / 5f).value.roundToInt()
        }
        val waveWidth = remember(maxWidth) {
            (maxWidth / numberOfWaves.toFloat()) * waveWidthPercentOfSpaceAvailable
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        updatedWaveInteraction.onInteraction(
                            ProgressPercentage.of(current = offset.x.toDp(), target = maxWidth),
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change: PointerInputChange, dragAmount: Float ->
                        // Do not trigger on minuscule movements
                        if (dragAmount.absoluteValue < 1f) return@detectHorizontalDragGestures
                        updatedWaveInteraction.onInteraction(
                            ProgressPercentage.of(
                                current = change.position.x.toDp(),
                                target = maxWidth
                            ),
                        )
                    }
                },
        ) {
            repeat(numberOfWaves) { waveIndex ->
                FakeAudioWavePill(
                    progressPercentage = progressPercentage,
                    numberOfWaves = numberOfWaves,
                    waveIndex = waveIndex,
                    playedColor = playedColor,
                    notPlayedColor = notPlayedColor,
                    modifier = Modifier.width(waveWidth),
                )
            }
        }
    }
}

private const val minWaveHeightFraction = 0.1f
private const val maxWaveHeightFractionForSideWaves = 0.1f
private const val maxWaveHeightFraction = 1.0f

@Composable
private fun FakeAudioWavePill(
    progressPercentage: ProgressPercentage,
    numberOfWaves: Int,
    waveIndex: Int,
    playedColor: Color,
    notPlayedColor: Color,
    modifier: Modifier = Modifier,
) {
    val height = remember(waveIndex, numberOfWaves) {
        val wavePosition = waveIndex + 1
        val centerPoint = numberOfWaves / 2
        val distanceFromCenterPoint = abs(centerPoint - wavePosition)
        val percentageToCenterPoint =
            ((centerPoint - distanceFromCenterPoint).toFloat() / centerPoint)
        val maxHeightFraction = lerp(
            maxWaveHeightFractionForSideWaves,
            maxWaveHeightFraction,
            percentageToCenterPoint,
        )
        if (maxHeightFraction <= minWaveHeightFraction) {
            maxHeightFraction
        } else {
            Random.nextDouble(minWaveHeightFraction.toDouble(), maxHeightFraction.toDouble())
                .toFloat()
        }
    }
    val hasPlayedThisWave = remember(progressPercentage, numberOfWaves, waveIndex) {
        progressPercentage.value * numberOfWaves > waveIndex
    }
    Surface(
        shape = CircleShape,
        color = if (hasPlayedThisWave) playedColor else notPlayedColor,
        modifier = modifier.fillMaxHeight(fraction = height),
    ) {}
}

@Preview
@Composable
private fun PreviewFakeAudioWaves() {
    ScrutinizingTheServiceTheme {
        val progress by animateFloatAsState(
            targetValue = 1f, label = "",
            animationSpec = tween(3000)
        )

        var someValue by remember {
            mutableStateOf(false)
        }

        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .height(150.dp)
                .clickable {
                    someValue = !someValue
                },
        ) {
            FakeAudioWaves(
                ProgressPercentage(
                    if (someValue)
                        progress
                    else
                        0f
                ),
                Color.Red,
                Color.Green,
                {},
            )
        }
    }
}