package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.local.Genre
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SongsUI(
    playMusicFromRemote: (RecentlyPlayed) -> Unit,
    viewModel: SongsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val songs = viewModel.songs.toPersistentList()

    val genres = viewModel.genres.toPersistentList()

    val selectedGenres = viewModel.selectedGenres.toPersistentList()

    AnimatedContent(
        targetState = genres.count { it.userSelected } == 0,
        label = "Genre selection",
        modifier = Modifier.fillMaxSize()
    ) {
        if (it) {
            GenreSelectionUI(
                state = state,
                genres = genres,
                selectedGenres = selectedGenres,
                onGenreClicked = { genre ->
                    viewModel.addRemoveToSelectedItems(genre)
                }, confirmGenreSelection = {
                    viewModel.confirmGenreSelection()
                }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreSelectionUI(
    state: SongsViewState,
    genres: PersistentList<Genre>,
    selectedGenres: PersistentList<Genre>,
    onGenreClicked: (Genre) -> Unit,
    confirmGenreSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Select maximum genres of your choice",
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            genres.forEachIndexed { index, genre ->
                GenreUiItem(
                    genre = genre,
                    onGenreClicked = { selectedGenre ->
                        onGenreClicked(genre)
                    },
                    isSelected = selectedGenres.contains(genre),
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = state.enforceSelection,
            modifier = Modifier
        ) {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )

                Text(
                    text = "Please select genres to proceed",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red),
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )

        Button(
            onClick = {
                confirmGenreSelection()
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
                        onItemClicked = {
                            songSelected(it)
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
            playMusicFromRemote = {}
        )
    }
}