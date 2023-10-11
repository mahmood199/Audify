package com.example.scrutinizing_the_service.v2.ui.landing

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Album
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Artist
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import com.example.scrutinizing_the_service.v2.ui.common.LoadMoreItemsRowUI
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun QuickPicksUI(
    modifier: Modifier = Modifier,
    viewModel: QuickPickViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val albums = viewModel.albums.toPersistentList()
    val songs = viewModel.songs.toPersistentList()
    val artists = viewModel.artists.toPersistentList()

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
                QuickPicksUIContent(
                    state = state,
                    albums = albums,
                    songs = songs,
                    artists = artists
                )
            }
        }
    }
}

@Composable
fun QuickPicksUIContent(
    state: QuickPickViewState,
    albums: PersistentList<Album>,
    songs: PersistentList<Song>,
    artists: PersistentList<Artist>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AlbumCatalogs(
            state = state,
            albums = albums
        )

        ArtistCatalogs(
            state = state,
            artists = artists,
            modifier = Modifier.fillMaxHeight(0.5f)
        )

        ArtistCatalogs(
            state = state,
            artists = artists,
            modifier = Modifier.fillMaxHeight()
        )

    }
}

@Composable
fun ArtistCatalogs(
    state: QuickPickViewState,
    artists: PersistentList<Artist>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Artists",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(
                count = artists.size,
                key = { index ->
                    artists[index].id + index
                }
            ) {
                ArtistItemUI(
                    artist = artists[it],
                    modifier = Modifier.fillParentMaxWidth(0.3f)
                )
            }
            item(
                key = "See More 2",
            ) {
                LoadMoreItemsRowUI(
                    modifier = Modifier
                        .fillParentMaxWidth(0.3f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun ArtistItemUI(
    artist: Artist,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = {
                ""
            },
            modifier = Modifier.clip(CircleShape),
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
                ContentLoaderUI(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        )

        Text(
            text = artist.name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun SongCatalogs(
    state: QuickPickViewState,
    songs: PersistentList<Song>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Songs",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(
            count = songs.size,
            key = { index ->
                songs[index].id + index
            }
        ) {
            SongItemUI(
                song = songs[it],
                modifier = Modifier.fillParentMaxWidth(0.3f)
            )
        }

        item(
            key = "See More 2",
        ) {
            LoadMoreItemsRowUI(
                modifier = Modifier
                    .fillParentMaxWidth(0.3f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun SongItemUI(
    song: Song,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = {
                song.image.last().link
            },
            modifier = Modifier.clip(CircleShape),
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
                ContentLoaderUI(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        )

        Text(
            text = song.name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCatalogs(
    state: QuickPickViewState,
    albums: PersistentList<Album>,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = "Albums",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        val lazyGridState = rememberLazyGridState()

        val snappingLayout = remember(lazyGridState) {
            SnapLayoutInfoProvider(
                lazyGridState = lazyGridState
            ) { _, _, _ -> lazyGridState.firstVisibleItemIndex }
        }

        val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {

            LazyHorizontalGrid(
                modifier = Modifier
                    .heightIn(maxHeight),
                state = lazyGridState,
                rows = GridCells.Fixed(4),
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
                    AlbumItemUI(
                        album = albums[it],
                        modifier = Modifier.width(maxWidth * 0.9f)
                    )
                }
                item(
                    key = "See More",
                    span = {
                        GridItemSpan(4)
                    }
                ) {
                    LoadMoreItemsRowUI(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(maxWidth * 0.5f)
                            .background(Color.Red)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumItemUI(
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
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
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