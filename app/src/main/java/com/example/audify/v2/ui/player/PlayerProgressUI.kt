package com.example.audify.v2.ui.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.ScrutinizingTheServiceTheme
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

@Composable
fun PlayerProgressUI() {

    val progress by animateFloatAsState(
        targetValue = 1f,
        label = "",
        animationSpec = tween(2000)
    )

    val random = remember {
        Random.Default
    }
    val bars = remember {
        List(25) {
            random.nextInt(
                15,
                61
            )
        }.toPersistentList()
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset.Zero,
                    size = Size(progress * size.width, size.height)
                )
            }
    ) {
        bars.forEachIndexed { index, it ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(it.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Transparent),
            ) {

            }
        }
    }
}


@Preview
@Composable
fun PlayerProgressUIPreview() {
    ScrutinizingTheServiceTheme {
        PlayerProgressUI()
    }
}