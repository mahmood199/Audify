package com.example.scrutinizing_the_service.v2.ui.home.quick_pick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.shimmerLoadingAnimation

@Composable
fun ShimmerDemoUI() {
    Column(
        modifier = Modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Column {
            ComponentRectangle()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentRectangleLineLong()
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentRectangleLineShort()
        }

        Spacer(modifier = Modifier.padding(24.dp))

        Row {
            ComponentCircle()
            Spacer(modifier = Modifier.padding(4.dp))
            Column {
                Spacer(modifier = Modifier.padding(8.dp))
                ComponentRectangleLineLong()
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentRectangleLineShort()
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))

        Row {
            ComponentSquare()
            Spacer(modifier = Modifier.padding(4.dp))
            Column {
                Spacer(modifier = Modifier.padding(8.dp))
                ComponentRectangleLineLong()
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentRectangleLineShort()
            }
        }
    }
}

@Composable
fun ComponentCircle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = Color.DarkGray)
            .size(100.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentSquare(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.DarkGray)
            .size(100.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.DarkGray)
            .height(200.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineLong(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.DarkGray)
            .size(height = 30.dp, width = 200.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineShort(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.DarkGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimation()
    )
}


@Preview
@Composable
fun ShimmerDemoUIPreview() {
    ScrutinizingTheServiceTheme {
        ShimmerDemoUI()
    }
}