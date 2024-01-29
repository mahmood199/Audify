package com.example.scrutinizing_the_service.v2.ui.core

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.v2.theme.ScrutinizingTheServiceTheme

@Composable
fun CustomRotationUIContainer(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CustomRotationUI(
            modifier = Modifier
                .align(Alignment.Center)
                .rotating(2500)
        )
    }
}

@Composable
fun CustomRotationUI(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(.25f)
            .aspectRatio(1f)
            .background(Color.Red)
    )
}

@Preview
@Composable
fun CustomRotationUIPreview() {
    ScrutinizingTheServiceTheme {
        CustomRotationUIContainer()
    }
}

fun Modifier.rotating(duration: Int): Modifier = this.then(composed {
    val transition = rememberInfiniteTransition("")
    val angleRatio by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(durationMillis = duration, delayMillis = 0, easing = LinearEasing)
        ), label = "Infinite rotation transition"
    )
    rotate(360 * angleRatio)
})