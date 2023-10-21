package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            ) {
                ContentLoaderUI(modifier = Modifier.fillMaxSize())
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    count = songs.size,
                    key = { index ->
                        songs[index].id + index
                    },
                    contentType = {
                        "Songs UI"
                    }
                ) { index ->
                    SongRowItemUI(
                        recentlyPlayed = songs[index],
                        onItemClicked = {

                        }
                    )
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