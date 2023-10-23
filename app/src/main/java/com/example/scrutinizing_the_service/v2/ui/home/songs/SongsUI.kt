package com.example.scrutinizing_the_service.v2.ui.home.songs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    viewModel: SongsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val songs = viewModel.songs.toPersistentList()

    val genres = viewModel.genres.toPersistentList()

    val selectedGenres = remember {
        mutableStateListOf<Genre>()
    }

    AnimatedContent(
        targetState = genres.count { it.userSelected } == 0,
        label = "Genre selection",
        modifier = Modifier.fillMaxSize()
    ) {
        if (it) {
            GenreSelectionUI(genres, selectedGenres)
        } else {
            ShowSongsContent(
                state = state,
                songs = songs
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreSelectionUI(
    genres: PersistentList<Genre>,
    selectedGenres: SnapshotStateList<Genre>
) {
    Column(
        modifier = Modifier
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            genres.forEachIndexed { index, genre ->
                GenreUiItem(
                    genre = genre,
                    onGenreClicked = { selectedGenre ->
                        if (selectedGenres.contains(selectedGenre))
                            selectedGenres.remove(selectedGenre)
                        else
                            selectedGenres.add(selectedGenre)
                    },
                    isSelected = selectedGenres.contains(genre)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )

        Button({

        }) {
            Text("Confirm selection")
        }
    }
}

@Composable
fun ShowSongsContent(
    state: SongsViewState,
    songs: PersistentList<RecentlyPlayed>,
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