package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SongsUI(
    viewModel: SongsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val songs = viewModel.songs.toPersistentList()

    AnimatedContent(state.isLoading, label = "Content Loader") {
        if (it) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                ContentLoaderUI(modifier = Modifier.fillMaxSize())
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn {
                    items(
                        count = songs.size,
                        key = { index ->
                            songs[index].id + index
                        },
                        contentType = {
                            "Songs UI"
                        }
                    ) { index ->
                        SongItemUI(
                            recentlyPlayed = songs[index],
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSongsUI() {
    ScrutinizingTheServiceTheme {
        SongsUI()
    }
}