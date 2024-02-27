package com.example.audify.v2.ui.home.landing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audify.v2.theme.AudifyTheme
import com.example.audify.v2.ui.audio_download.DownloadCenterUI
import com.example.audify.v2.ui.common.SideNavigationBar
import com.example.audify.v2.ui.home.album.AlbumsUI
import com.example.audify.v2.ui.home.artist.ArtistsUI
import com.example.audify.v2.ui.home.favourites.FavouritesUI
import com.example.audify.v2.ui.home.playlist.PlaylistUI
import com.example.audify.v2.ui.home.quick_pick.QuickPicksUI
import com.example.audify.v2.ui.home.songs.SongsUI
import com.example.data.models.remote.saavn.Song
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydiver.audify.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LandingPageUI(
    redirectToGenreSelection: () -> Unit,
    redirectToLocalAudioScreen: () -> Unit,
    navigateToSearch: () -> Unit,
    backPress: () -> Unit,
    playMusicFromRemote: (Song) -> Unit,
    navigateToPlayer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LandingPageViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val headers = getHeaders()

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(value = state.userSelectedPage)
    }

    val pagerState = rememberPagerState(
        initialPage = state.userSelectedPage,
        initialPageOffsetFraction = 0f
    ) {
        headers.size
    }

    val coroutineScope = rememberCoroutineScope()

    val uiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        uiController.setNavigationBarColor(Color.Transparent)
    }
    val snackBarHostState = remember { SnackbarHostState() }

    val dismissSnackBarState = rememberSwipeToDismissBoxState(confirmValueChange = { value ->
        if (value != SwipeToDismissBoxValue.Settled) {
            snackBarHostState.currentSnackbarData?.dismiss()
            true
        } else {
            false
        }
    })

    LaunchedEffect(dismissSnackBarState.currentValue) {
        if (dismissSnackBarState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissSnackBarState.reset()
        }
    }

    LaunchedEffect(state.isConnected) {
        if (state.isConnected) {
            snackBarHostState.showSnackbar(
                message = "Connected to Internet",
                withDismissAction = true
            )
        } else {
            snackBarHostState.showSnackbar(
                message = "Internet connection lost",
                withDismissAction = true
            )
        }
    }

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
        bottomBar = {
            AnimatedBottomPlayer(
                state = state,
                isShown = state.isPlaying,
                sendMediaAction = viewModel::sendMediaAction,
                sendUiEvent = viewModel::sendUIEvent,
                navigateToPlayer = navigateToPlayer,
                modifier = Modifier.navigationBarsPadding()
            )
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
        snackbarHost = {
            SwipeToDismissBox(
                state = dismissSnackBarState,
                backgroundContent = {

                },
                content = {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier
                            .imePadding()
                    )
                },
            )
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

                PagerContent(
                    modifier = Modifier.weight(1f),
                    pagerState = pagerState,
                    redirectToGenreSelection = redirectToGenreSelection,
                    playMusicFromRemote = playMusicFromRemote,
                    redirectToLocalAudioScreen = redirectToLocalAudioScreen
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerContent(
    pagerState: PagerState,
    redirectToGenreSelection: () -> Unit,
    redirectToLocalAudioScreen: () -> Unit,
    playMusicFromRemote: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        VerticalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { currentPage ->
            when (currentPage) {

                QUICK_PICKS_PAGE_INDEX -> QuickPicksUI()

                SONGS_PAGE_INDEX -> SongsUI(
                    redirectToGenreSelection = redirectToGenreSelection,
                    playMusicFromRemote = { recentlyPlayed ->
                        playMusicFromRemote(recentlyPlayed)
                    }
                )

                PLAYLIST_PAGE_INDEX -> PlaylistUI(
                    goToLocalAudioScreen = redirectToLocalAudioScreen,
                )

                ARTIST_PAGE_INDEX -> ArtistsUI()

                ALBUM_PAGE_INDEX -> AlbumsUI()

                FAVOURITES_PAGE_INDEX -> FavouritesUI()

                DOWNLOADS_PAGE_INDEX -> DownloadCenterUI(
                    onDownloadItem = { item, index ->

                    }
                )

                else -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }
        }
    }

}

private const val QUICK_PICKS_PAGE_INDEX = 0
private const val SONGS_PAGE_INDEX = 1
private const val PLAYLIST_PAGE_INDEX = 2
private const val ARTIST_PAGE_INDEX = 3
private const val ALBUM_PAGE_INDEX = 4
private const val FAVOURITES_PAGE_INDEX = 5
private const val DOWNLOADS_PAGE_INDEX = 6


@Composable
private fun getHeaders(): PersistentList<Pair<String, ImageVector>> {
    return listOf(
        Pair("Quick Picks", ImageVector.vectorResource(R.drawable.ic_music_note)),
        Pair("Songs", ImageVector.vectorResource(R.drawable.ic_library_music)),
        Pair("Playlists", ImageVector.vectorResource(R.drawable.ic_artist)),
        Pair("Artists", ImageVector.vectorResource(R.drawable.ic_artist_2)),
        Pair("Albums", ImageVector.vectorResource(R.drawable.ic_album_2)),
        Pair("Favourites", ImageVector.vectorResource(R.drawable.ic_favorite_filled)),
        Pair("Downloads", ImageVector.vectorResource(R.drawable.ic_file_download_notification)),
    ).toPersistentList()
}


@Preview
@Composable
fun PreviewLandingPageUI() {
    AudifyTheme {
        LandingPageUI(
            redirectToLocalAudioScreen = {

            },
            navigateToSearch = {},
            backPress = {},
            playMusicFromRemote = {},
            redirectToGenreSelection = {},
            navigateToPlayer = {}
        )
    }
}