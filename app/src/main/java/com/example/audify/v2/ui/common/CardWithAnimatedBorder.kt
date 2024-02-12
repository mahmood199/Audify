package com.example.audify.v2.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.AudifyTheme

@Composable
fun CardWithAnimatedBorderUI(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
    cornerSize: Dp = 16.dp,
    borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1800f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(
                durationMillis = 15000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = if (borderColors.isNotEmpty()) Brush.sweepGradient(borderColors)
    else Brush.sweepGradient(
        colors = listOf(
            Color.DarkGray,
            Color.Gray,
            Color.LightGray,
            Color.White,
            Color.DarkGray
        )
    )

    Surface(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onCardClick()
            }
            .clip(RoundedCornerShape(cornerSize)),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = maxOf(size.width, size.height),
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                }
                .padding(6.dp)
                .clip(RoundedCornerShape(cornerSize))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(cornerSize))
                    .padding(cornerSize * 0.75f)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun CardWithAnimatedBorderUIPreview() {
    AudifyTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CardWithAnimatedBorderUI(
                onCardClick = {

                },
                borderColors = listOf(
                    Color.Red,
                    Color.Yellow,
                    Color.Green,
                    Color.Blue,
                    Color.Magenta,
                    Color.Black,
                    Color.Red,
                ),
                cornerSize = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.3f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text("Hello")
                    Text("Hello")
                    Text("Hello")
                    Text("Hello")
                }
            }
        }
    }
}