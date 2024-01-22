package com.example.scrutinizing_the_service.v2.ui.core.custom_border


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Preview
@Composable
fun DottedColorBorderPreview() {
    ScrutinizingTheServiceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .aspectRatio(1.5f)
                    .align(Alignment.Center)
                    .dottedBorder(Color.Red)
            )
        }
    }
}

fun Modifier.dottedBorder(color: Color): Modifier {
    return this.then(DottedBorder(color))
}


class DottedBorder(
    var color: Color
) : DrawModifier {

    override fun ContentDrawScope.draw() {
        drawContent()
        val radius = minOf(size.width, size.height) / 10
        drawOutline(
            outline = Outline.Rounded(
                roundRect = RoundRect(
                    cornerRadius = CornerRadius(radius, radius),
                    rect = Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(
                            x = size.width,
                            y = size.height
                        )
                    )
                )
            ),
            color = color,
            style = Stroke(
                width = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 12f))
            )
        )
    }
}

