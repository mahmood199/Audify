package com.example.audify.v2.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout


fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }

fun Modifier.shimmerLoadingAnimation(
    isLoading: Boolean = true,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    if (!isLoading) {
        return this
    } else {
        return composed {

            val shimmers = remember(Unit) {
                listOf(
                    Color.White.copy(alpha = .3f),
                    Color.White.copy(alpha = .3f),
                    Color.White.copy(alpha = .5f),
                    Color.White.copy(alpha = .3f),
                    Color.White.copy(alpha = .3f),
                )
            }

            val transition = rememberInfiniteTransition("Shimmer Transition")

            val translateAnimation by transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "Shimmer loading animation",
            )


            this.background(
                brush = Brush.linearGradient(
                    colors = shimmers,
                    start = Offset(x = translateAnimation - widthOfShadowBrush, y = 0.0f),
                    end = Offset(x = translateAnimation, y = angleOfAxisY)
                ),
            )

        }
    }
}