package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SongsUI(
    redirectToGenreSelection: () -> Unit,
    playMusicFromRemote: (RecentlyPlayed) -> Unit,
    viewModel: SongsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val songs = viewModel.songs.toPersistentList()

    val genres = viewModel.genres.toPersistentList()

    AnimatedContent(
        targetState = genres.count { it.userSelected } == 0,
        label = "Genre selection",
        modifier = Modifier.fillMaxSize()
    ) {
        if (it) {
            GoToGenreSelectionScreen(
                redirectToGenreSelection = redirectToGenreSelection
            )
        } else {
            SongsContentUI(
                state = state,
                songs = songs,
                songSelected = { recentlyPlayed ->
                    viewModel.playItem(recentlyPlayed)
                    playMusicFromRemote(recentlyPlayed)
                }
            )
        }
    }
}

@Composable
fun GoToGenreSelectionScreen(
    redirectToGenreSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Go to genre selection screen to select the song genres of your preference",
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = {
                redirectToGenreSelection()
            }
        ) {
            Text("Confirm selection")
        }

    }
}

@Composable
fun SongsContentUI(
    state: SongsViewState,
    songs: PersistentList<RecentlyPlayed>,
    songSelected: (RecentlyPlayed) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        modifier = modifier.fillMaxSize(),
        targetState = state.isLoading,
        label = "Songs Content"
    ) {
        if (it) {
            ContentLoaderUI(modifier = Modifier.fillMaxSize())
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
                    val song = remember(songs[index]) {
                        songs[index]
                    }
                    SongRowItemUI(
                        recentlyPlayed = song,
                        onItemClicked = { recentlyPlayed ->
                            songSelected(recentlyPlayed)
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
        SongsUI(
            redirectToGenreSelection = {},
            playMusicFromRemote = {}
        )
    }
}