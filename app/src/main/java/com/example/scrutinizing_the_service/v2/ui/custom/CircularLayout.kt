package com.example.scrutinizing_the_service.v2.ui.custom

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.roundToIntRect
import com.example.scrutinizing_the_service.compose_utils.SaveableLaunchedEffect
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.core.rotating
import com.example.scrutinizing_the_service.v2.util.px
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * Lays out it's children in a circular manner based on the specified
 * radius
 *
 * @param content The composable content that will be laid out in a
 *     circular manner.
 * @param radius The radius of the circular layout. The children will be
 *     positioned on a circle with this radius.
 */
@Composable
fun CircularLayout(
    pointer: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    radius: Float = 250f
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map {
            it.measure(constraints = constraints)
        }

        val angularSeparation = 360f / placeables.size

        val boundedRectangle = Rect(
            center = Offset(0f, 0f),
            radius = radius + placeables.maxOf { it.height }
        ).roundToIntRect()

        val handHeight = radius * 0.75f

        val center = IntOffset(boundedRectangle.width / 2, boundedRectangle.height / 2)

        layout(boundedRectangle.width, boundedRectangle.height) {
            var requiredAngle = 180.0

            placeables.forEach { placeable ->
                // Calculate x,y coordinates where the layout will be placed on the
                // circumference of the circle using the required angle
                val x = center.x + (radius * sin(Math.toRadians(requiredAngle))).toInt()
                val y = center.y + (radius * cos(Math.toRadians(requiredAngle))).toInt()

                placeable.placeRelative(x - placeable.width / 2, y - placeable.height / 2)

                requiredAngle -= angularSeparation
            }
        }
    }
}

@Preview
@Composable
fun CircularLayoutPreview() {
    ScrutinizingTheServiceTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            var clickCoordinate by remember {
                mutableStateOf(Offset.Zero)
            }

            val items = remember(Unit) {
                getHours()
            }

            var currentTimeClicked by remember {
                mutableIntStateOf(value = items.first())
            }

            var touchCoordinate by remember {
                mutableStateOf(Offset.Zero)
            }

            val context = LocalContext.current

            CircularLayout(
                pointer = 5,
                radius = 400f,
                content = {
                    items.map { hour ->
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(
                                    color = Color.Red,
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        clickCoordinate = it
                                        currentTimeClicked = hour
                                    }
                                }
                        ) {
                            val textToShow = remember(hour) {
                                "$hour"
                            }
                            Text(
                                text = textToShow,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clip(CircleShape)
                            )
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .pointerInput(Unit) {
                        detectTapGestures {

                        }
                        detectDragGestures { change, dragAmount ->

                        }
                    }
            )

            LaunchedEffect(clickCoordinate) {
                Toast.makeText(context, "$clickCoordinate", Toast.LENGTH_SHORT).show()
            }

            BoxWithConstraints(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
                    .align(Alignment.Center)
                    .pointerInput(Unit) {
                        detectTapGestures (
                            onTap = {
                                clickCoordinate = it
                            }
                        )
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            clickCoordinate = Offset(change.position.x, change.position.y)
                        }
                    }
            ) {
                val height = maxHeight
                val width = maxWidth

                val context = LocalContext.current

                SaveableLaunchedEffect(Unit) {
                    clickCoordinate = Offset(
                        x = width.value.px(context) * 0.85f,
                        y = height.value * 0.0f
                    )
                }

                val textMeasurer = rememberTextMeasurer()

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(Color.Yellow)
                        .clipToBounds()
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                clickCoordinate = Offset(change.position.x, change.position.y)
                            }
                            detectTapGestures {
                                clickCoordinate = it
                            }
                        }
                ) {
                    val size = this.size

                    val centerX = size.width * 0.5f
                    val centerY = size.height * 0.5f

                    val radius = size.width * 0.5f

                    val angle = calculateAngle(
                        center = Offset(x = centerX, y = centerY),
                        point = Offset(x = clickCoordinate.x, y = clickCoordinate.y)
                    )

                    val pointOnCircle = calculatePointOnCircumference(
                        Offset(x = centerX, y = centerY),
                        radius,
                        angle
                    )

                    drawText(textMeasurer.measure(pointOnCircle.toString()))

                    drawLine(
                        color = Color.Red,
                        start = Offset(size.width * 0.5f, size.height * 0.5f),
                        end = clickCoordinate,
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = Color.Red,
                        start = Offset(size.width * 0.5f, size.height * 0.5f),
                        end = pointOnCircle,
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )


                }

            }
        }
    }
}

fun calculateAngle(center: Offset, point: Offset): Float {
    return atan2(point.y - center.y, point.x - center.x)
}

fun calculatePointOnCircumference(center: Offset, radius: Float, angle: Float): Offset {
    val x = center.x + radius * cos(angle)
    val y = center.y + radius * sin(angle)
    return Offset(x.toFloat(), y.toFloat())
}

fun getHours(): List<Int> {
    return listOf(
        12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
    )
}
