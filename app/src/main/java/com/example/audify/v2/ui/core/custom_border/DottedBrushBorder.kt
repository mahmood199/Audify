package com.example.audify.v2.ui.core.custom_border

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.example.audify.v2.theme.ScrutinizingTheServiceTheme


@Preview
@Composable
fun DottedBrushBorderPreview() {
    ScrutinizingTheServiceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1.5f)
                    .align(Alignment.Center)
                    .dottedBrushBorder(
                        Brush.radialGradient(
                            colors = listOf(Color.Red, Color.Green, Color.Blue),
                            radius = 400f,
                        )
                    )
            )
        }
    }
}

fun Modifier.dottedBrushBorder(brush: Brush): Modifier {
    return this.then(DottedBrushBorder(brush))
}

class DottedBrushBorder(
    var brush: Brush
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
            brush = brush,
            style = Stroke(
                width = 8f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 12f))
            )
        )
    }

}
