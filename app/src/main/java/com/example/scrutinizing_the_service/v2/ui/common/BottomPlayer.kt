package com.example.scrutinizing_the_service.v2.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomPlayer(
    progress: Float,
    songName: String,
    artist: String,
    isPlaying: Boolean,
    onPlayPauseClicked: () -> Unit,
    onRewindClicked: () -> Unit,
    onFastForwardClicked: () -> Unit,
    onPlayPreviousClicked: () -> Unit,
    onPlayNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = progress,
            trackColor = Color.Red,
            color = Color.Cyan,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(all = 6.dp)
        ) {
            Image(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Song Thumbnail"
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = songName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    modifier = Modifier.basicMarquee(300),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = artist,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_rewind),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .border(width = 2.dp, color = Color.Black, CircleShape)
                            .padding(2.dp)
                            .clickable {
                                onRewindClicked()
                            }
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (isPlaying)
                                R.drawable.ic_pause
                            else
                                R.drawable.ic_play
                        ),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .border(width = 2.dp, color = Color.Black, CircleShape)
                            .padding(8.dp)
                            .clickable {
                                onPlayPauseClicked()
                            }
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_fast_forward),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .border(width = 2.dp, color = Color.Black, CircleShape)
                            .padding(2.dp)
                            .clickable {
                                onFastForwardClicked()
                            }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_skip_previous),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .border(width = 2.dp, color = Color.Black, CircleShape)
                            .padding(2.dp)
                            .clickable {
                                onPlayPreviousClicked()
                            }
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_skip_next),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .border(width = 2.dp, color = Color.Black, CircleShape)
                            .padding(2.dp)
                            .clickable {
                                onPlayNextClicked()
                            }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun BottomPlayerPreview() {
    ScrutinizingTheServiceTheme {
        BottomPlayer(
            progress = 0.7f,
            songName = "Some Random Long Name Song",
            artist = "Some Artist",
            isPlaying = true,
            onPlayNextClicked = {},
            onPlayPauseClicked = {},
            onFastForwardClicked = {},
            onPlayPreviousClicked = {},
            onRewindClicked = {}
        )
    }
}