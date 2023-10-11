package com.example.scrutinizing_the_service.v2.ui.landing

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.collections.immutable.toPersistentList

@Composable
fun QuickPicksUI(
    viewModel: QuickPickViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val albums = viewModel.albums.toPersistentList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {

        val lazyListState = rememberLazyListState()

        Crossfade(
            targetState = state.isLoading,
            label = "CrossFade"
        ) { isLoading ->
            if (isLoading) {
                ContentLoaderUI()
            } else {
                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        count = albums.size,
                        key = {
                            albums[it].id
                        },
                        contentType = {
                            ""
                        }
                    ) {
                        AlbumV2UI(albums[it])
                    }
                }
            }
        }

    }
}

@Composable
fun AlbumV2UI(album: Album) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            imageModel = {
                if (album.image.isEmpty()) {
                    ""
                } else {
                    album.image[0].link
                }
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
            Text(text = album.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = album.id, style = MaterialTheme.typography.titleMedium)
        }
    }
}


@Preview
@Composable
fun PreviewQuickPicksUI() {
    ScrutinizingTheServiceTheme {
        QuickPicksUI()
    }
}