package com.example.audify.v2.ui.genre

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audify.v2.theme.ScrutinizingTheServiceTheme
import com.example.audify.v2.ui.home.songs.GenreUiItem
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreSelectionUI(
    modifier: Modifier = Modifier,
    viewModel: GenresViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val genres by viewModel.genres.collectAsStateWithLifecycle()

    val selectedGenres = viewModel.selectedGenres.toPersistentList()

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
                        viewModel.addRemoveToSelectedItems(selectedGenre)
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
                viewModel.confirmGenreSelection()
            }
        ) {
            Text("Confirm selection")
        }
    }
}


@Preview
@Composable
fun GenreSelectionUIPreview() {
    ScrutinizingTheServiceTheme {
        GenreSelectionUI()
    }
}
