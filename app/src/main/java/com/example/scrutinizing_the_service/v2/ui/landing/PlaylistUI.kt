package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun PlaylistUI(
    goToLocalAudioScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(all = 12.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(15))
                    .background(Color.Magenta)
                    .aspectRatio(1f)
                    .clickable {
                        goToLocalAudioScreen()
                    }
            ) {
                Text(text = "Offline", modifier = Modifier.align(Alignment.Center))
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(15))
                    .background(Color.Green)
                    .aspectRatio(1f)
            ) {
                Text(text = "Custom", modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}

@Preview
@Composable
fun PreviewPlaylistUI() {
    ScrutinizingTheServiceTheme {
        PlaylistUI(goToLocalAudioScreen = {})
    }
}