package com.example.audify.v2.ui.home.songs

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.audify.v2.theme.AudifyTheme
import com.example.audify.v2.ui.common.ContentLoaderUI
import com.example.audify.v2.util.isEmpty
import com.example.data.models.remote.saavn.Song

@Composable
fun SongsUI(
    navigateToGenreSelection: () -> Unit,
    playMusicFromRemote: (Song) -> Unit,
    viewModel: SongsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val songs = viewModel.songs.collectAsLazyPagingItems()

    val genres by viewModel.genres.collectAsStateWithLifecycle()

    DisposableEffect(viewModel) {
        viewModel.setupPagingSource()
        Log.d("SongsUI", "Collection started")
        onDispose {
            Log.d("SongsUI", "Collection Ended")
            viewModel.onStop()
        }
    }

    AnimatedContent(targetState = genres.count { it.userSelected } == 0,
        label = "Genre selection",
        modifier = Modifier.fillMaxSize()) {
        if (it) {
            GoToGenreSelectionScreen(
                redirectToGenreSelection = navigateToGenreSelection
            )
        } else {
            SongsContentUI(state = state,
                songs = songs,
                redirectToGenreSelection = navigateToGenreSelection,
                songSelected = { recentlyPlayed ->
                    viewModel.playItem(recentlyPlayed)
                    playMusicFromRemote(recentlyPlayed)
                },
                updateFavourite = { recentlyPlayed ->

                })
        }
    }
}

@Composable
fun GoToGenreSelectionScreen(
    redirectToGenreSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Go to genre selection screen to select the song genres of your preference",
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            redirectToGenreSelection()
        }) {
            Text("Confirm selection")
        }

    }
}

@Composable
fun SongsContentUI(
    state: SongsViewState,
    songs: LazyPagingItems<Song>,
    redirectToGenreSelection: () -> Unit,
    songSelected: (Song) -> Unit,
    updateFavourite: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        modifier = modifier.fillMaxSize(), targetState = state.isLoading, label = "Songs Content"
    ) {
        if (it) {
            ContentLoaderUI(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Button(onClick = {
                        redirectToGenreSelection()
                    }) {
                        Text("Go to select genre")
                    }
                }
                item {
                    if (songs.isEmpty) {
                        Text("No songs submitted to UI")
                    } else {
                        Text("Songs submitted to UI")
                    }
                }
                items(count = songs.itemCount, key = { index ->
                    songs[index]?.id + index
                }, contentType = {
                    "Songs UI"
                }) { index ->
                    val song = remember(songs[index]) {
                        songs[index]
                    }

                    if (song != null) {
                        SongRowItemUI(
                            song = song,
                            onItemClicked = { recentlyPlayed ->
                                songSelected(recentlyPlayed)
                            }, updateFavourite = { recentlyPlayed ->
                                updateFavourite(recentlyPlayed)
                            })
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSongsUI() {
    AudifyTheme {
        SongsUI(
            navigateToGenreSelection = {},
            playMusicFromRemote = {},
        )
    }
}