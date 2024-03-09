package com.example.audify.v2.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.AudifyTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SideNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    headers: PersistentList<Pair<String, ImageVector>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(all = 6.dp),
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        headers.forEachIndexed { index, it ->
            Header(
                it = it,
                isSelected = index == selectedIndex,
                onSelected = {
                    onItemSelected(index)
                }
            )
        }
    }
}

@Composable
fun Header(
    it: Pair<String, ImageVector>,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
    defaultContentColor: Color = Color.Black,
    defaultScale: Float = 1f
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.25f else defaultScale,
        animationSpec = TweenSpec(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ), label = "Scale Animation"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else defaultContentColor,
        animationSpec = TweenSpec(
            durationMillis = 250,
            easing = LinearEasing
        ), label = "Color Animation"
    )

    Column(
        modifier = modifier
            .vertical()
            .rotate(-90f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSelected()
            }
            .semantics {
                contentDescription = it.first
            }
            .scale(animatedScale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Icon(
                imageVector = it.second,
                contentDescription = it.first,
                tint = Color.Transparent,
                modifier = Modifier.size(24.dp)
            )
            Column {
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn() + slideInVertically {
                        -100
                    },
                    exit = fadeOut() + slideOutVertically {
                        -100
                    }
                ) {
                    Icon(
                        imageVector = it.second,
                        contentDescription = it.first,
                        tint = animatedColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Text(
            text = it.first,
            color = animatedColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


@Preview
@Composable
fun SideNavigationBarUI() {
    AudifyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            SideNavigationBar(
                selectedIndex = 0,
                headers = listOf(
                    Pair("Track", Icons.Default.Search),
                    Pair("Album", Icons.Default.Search),
                    Pair("Artist", Icons.Default.Search),
                    Pair("Tag", Icons.Default.Search),
                    Pair("Something", Icons.Default.Search),
                ).toPersistentList(),
                onItemSelected = {},
                modifier = Modifier.padding(top = 200.dp)
            )
        }
    }
}