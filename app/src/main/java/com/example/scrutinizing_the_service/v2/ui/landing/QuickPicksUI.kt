package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickPicksUI(
    modifier: Modifier = Modifier,
    viewModel: QuickPickViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val albums = viewModel.albums.toPersistentList()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = state.isLoading,
            label = "CrossFade"
        ) { isLoading ->
            if (isLoading) {
                ContentLoaderUI()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Albums",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    val lazyGridState = rememberLazyGridState()

                    val snappingLayout = remember(lazyGridState) {
                        SnapLayoutInfoProvider(
                            lazyGridState = lazyGridState
                        ) { _, _, _ -> 0 }
                    }
                    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    ) {
                        LazyHorizontalGrid(
                            state = lazyGridState,
                            rows = GridCells.Fixed(4),
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            flingBehavior = flingBehavior,
                        ) {
                            items(
                                count = albums.size,
                                key = {
                                    albums[it].id
                                },
                                contentType = {
                                    "album"
                                }
                            ) {
                                AlbumV2UI(
                                    album = albums[it],
                                    modifier = Modifier.width(maxWidth * 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumV2UI(
    album: Album,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            GlideImage(
                imageModel = {
                    if (album.image.isEmpty()) {
                        ""
                    } else {
                        album.image.last().link
                    }
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillBounds
                ),
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(0.4f)
                    .clip(RoundedCornerShape(10)),
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
                    ContentLoaderUI(modifier = Modifier.fillMaxSize())
                }
            )
            Column(
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.id,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
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