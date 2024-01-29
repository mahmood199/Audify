package com.example.scrutinizing_the_service.v2.ui.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun BorderRotateUI(
    modifier: Modifier = Modifier
) {
    val colorBg = Color(0xFF2C3141)

    val borderColors = remember {
        listOf(
            Color.Red,
            Color.Yellow,
            Color.Green,
            Color.Blue,
            Color.Magenta,
            Color.Black,
            Color.Red,
        )
    }

    val brush = Brush.sweepGradient(
        colors = borderColors,
    )

    val infiniteTransition =
        rememberInfiniteTransition(label = "Border Animation Infinite Transition")
    val angle by infiniteTransition.animateFloat(
        0f,
        720f,
        label = "Circle Angle Animation",
        animationSpec = infiniteRepeatable(
            animation = TweenSpec(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6))
            .background(color = colorBg)
    ) {

        val width = size.width
        val height = size.height

        val radius = minOf(width, height) / 15f

        rotate(angle) {
            drawCircle(
                brush = brush,
                radius = height,
                blendMode = BlendMode.SrcIn
            )
        }

        drawRoundRect(
            color = colorBg,
            topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
            size = Size(
                width = size.width - 8.dp.toPx(),
                height = size.height - 8.dp.toPx()
            ),
            cornerRadius = CornerRadius(
                radius
            )
        )

    }
}


@Preview
@Composable
fun BorderRotateUIPreview() {
    ScrutinizingTheServiceTheme {
        BorderRotateUI()
    }
}