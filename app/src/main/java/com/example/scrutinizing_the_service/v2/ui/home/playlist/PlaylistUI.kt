package com.example.scrutinizing_the_service.v2.ui.home.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.v2.theme.ScrutinizingTheServiceTheme
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistUI(
    goToLocalAudioScreen: () -> Unit,
    viewModel: PlaylistViewModel = hiltViewModel()
) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    var playlistName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(RoundedCornerShape(15))
                .background(Color.DarkGray)
                .clickable {
                    showDialog = true
                }
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Playlist",
                    modifier = Modifier.size(64.dp)
                )
                Text("Create Playlist")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    playlistName = ""
                },
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .background(Color.White)
                        .padding(vertical = 24.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Enter the playlist name")
                    TextField(
                        value = playlistName,
                        onValueChange = {
                            playlistName = it
                        },
                        modifier = Modifier.background(Color.Transparent),
                        maxLines = 1,
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            viewModel.createPlaylist()
                            showDialog = false
                        }
                    ) {
                        Text("Create Playlist")
                    }
                }
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