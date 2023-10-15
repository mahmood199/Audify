package com.example.scrutinizing_the_service.v2.ui.home.landing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.SideNavigationBar
import com.example.scrutinizing_the_service.v2.ui.home.playlist.PlaylistUI
import com.example.scrutinizing_the_service.v2.ui.home.quick_pick.QuickPicksUI
import com.example.scrutinizing_the_service.v2.ui.home.songs.SongsUI
import com.example.scrutinizing_the_service.v2.ui.home.album.AlbumsUI
import com.example.scrutinizing_the_service.v2.ui.home.artist.ArtistsUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandingPageUI(
    redirectToLocalAudioScreen: () -> Unit,
    navigateToSearch: () -> Unit,
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LandingPageViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val headers = getHeaders()

    var selectedIndex by remember {
        mutableIntStateOf(value = state.userSelectedPage)
    }

    val pagerState = rememberPagerState(
        initialPage = state.userSelectedPage,
        initialPageOffsetFraction = 0f
    ) {
        headers.size
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 64.dp, bottom = 16.dp)
                    .padding(start = 32.dp, end = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_account_settings
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            backPress()
                        }
                )
                Text(
                    text = headers[selectedIndex].first,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToSearch() },
                modifier = Modifier
                    .clip(RoundedCornerShape(25))
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Song Button"
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(start = 6.dp)
            ) {
                SideNavigationBar(
                    headers = headers,
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                    modifier = Modifier.padding(top = 40.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    VerticalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { currentPage ->
                        when (currentPage) {

                            QUICK_PICKS_PAGE_INDEX -> QuickPicksUI()

                            SONGS_PAGE_INDEX -> SongsUI()

                            PLAYLIST_PAGE_INDEX -> PlaylistUI(
                                goToLocalAudioScreen = redirectToLocalAudioScreen,
                            )

                            ARTIST_PAGE_INDEX -> ArtistsUI()

                            ALBUM_PAGE_INDEX -> AlbumsUI()

                            else -> Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val QUICK_PICKS_PAGE_INDEX = 0
private const val SONGS_PAGE_INDEX = 1
private const val PLAYLIST_PAGE_INDEX = 2
private const val ARTIST_PAGE_INDEX = 3
private const val ALBUM_PAGE_INDEX = 4


@Composable
private fun getHeaders(): PersistentList<Pair<String, ImageVector>> {
    return listOf(
        Pair("Quick Picks", ImageVector.vectorResource(R.drawable.ic_music_note)),
        Pair("Songs", ImageVector.vectorResource(R.drawable.ic_library_music)),
        Pair("Playlists", ImageVector.vectorResource(R.drawable.ic_artist)),
        Pair("Artists", ImageVector.vectorResource(R.drawable.ic_artist_2)),
        Pair("Albums", ImageVector.vectorResource(R.drawable.ic_album_2)),
        Pair("Favourites", ImageVector.vectorResource(R.drawable.ic_favorite_filled)),
    ).toPersistentList()
}


@Preview
@Composable
fun PreviewLandingPageUI() {
    ScrutinizingTheServiceTheme {
        LandingPageUI(
            redirectToLocalAudioScreen = {

            },
            navigateToSearch = {},
            backPress = {}
        )
    }
}