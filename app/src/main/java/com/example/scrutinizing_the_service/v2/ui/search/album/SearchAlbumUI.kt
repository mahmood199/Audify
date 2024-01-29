package com.example.scrutinizing_the_service.v2.ui.search.album

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.data.models.remote.last_fm.Album
import com.example.scrutinizing_the_service.v2.paging.isAppending
import com.example.scrutinizing_the_service.v2.paging.isEmpty
import com.example.scrutinizing_the_service.v2.paging.isFirstLoad
import com.example.scrutinizing_the_service.v2.ui.search.result.SearchResultState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SearchAlbumUI(
    modifier: Modifier = Modifier,
    viewModel: SearchAlbumViewModel = hiltViewModel()
) {
    val items = viewModel.albums.collectAsLazyPagingItems()

    val loadTargetState: SearchResultState by remember {
        derivedStateOf {
            return@derivedStateOf when {
                items.isEmpty.not() -> SearchResultState.Success

                items.isFirstLoad -> SearchResultState.Loading

                else -> SearchResultState.Error
            }
        }
    }

    Crossfade(
        targetState = loadTargetState,
        label = "Album Search transition",
        modifier = modifier.fillMaxSize()
    ) {
        when (it) {
            SearchResultState.Error -> {

            }

            SearchResultState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            SearchResultState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(
                            count = items.itemCount,
                            key = {
                                val key: (index: Int) -> Any = items.itemKey { item ->
                                    item.mbid + "$it"
                                }
                                key(it)
                            },
                            contentType = items.itemContentType { "albums" },
                        ) { index ->
                            val item = items[index]
                            if (item != null) {
                                AlbumUI(item)
                            }
                        }

                        if (items.isAppending) {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumUI(item: Album) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            imageModel = {
                item.images[1].text
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillBounds
            ),
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(15)),
            failure = {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_album_place_holder
                    ),
                    contentDescription = "Album place holder",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .background(Color.Gray)
                        .padding(12.dp)
                )
            },
            loading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.artist, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview
@Composable
fun SearchAlbumUIPreview() {
    ScrutinizingTheServiceTheme {
        SearchAlbumUI()
    }
}