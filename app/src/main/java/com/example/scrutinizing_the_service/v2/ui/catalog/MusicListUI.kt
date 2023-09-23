package com.example.scrutinizing_the_service.v2.ui.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.compose_utils.SaveableLaunchedEffect
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.ui.common.AppBar
import com.example.scrutinizing_the_service.v2.ui.common.BottomPlayer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MusicListUI(
    playMusic: (Song, Int) -> Unit,
    backPress: () -> Unit,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    val permissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val state by viewModel.state.collectAsState()

    val isShown by remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex + 1) < listState.layoutInfo.visibleItemsInfo.count() / 2
        }
    }

    val songs = viewModel.songs

    BackHandler {
        backPress()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    imageVector = Icons.Default.ArrowBack,
                    title = "Catalog",
                    backPressAction = backPress,
                )
            },
            bottomBar = {
                AnimatedBottomPlayer(
                    state = state,
                    isShown = true,
                    sendUiEvent = viewModel::sendUIEvent
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                AnimatedFAB(isShown, navigateToSearch)
            },
            modifier = modifier
                .background(Color.White)
                .fillMaxSize()
        ) { paddingValues ->

            PermissionRequired(
                isLoading = state.loading,
                songs = songs,
                lazyListState = listState,
                permissionState = permissionState,
                permissionGranted = {
                    viewModel.getDeviceAudios()
                },
                sendUiEvent = viewModel::sendUIEvent,
                playMusic = playMusic,
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequired(
    isLoading: Boolean,
    songs: List<Song>,
    lazyListState: LazyListState,
    permissionState: PermissionState,
    permissionGranted: () -> Unit,
    sendUiEvent: (MusicListUiEvent) -> Unit,
    playMusic: (Song, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SaveableLaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }
    val result = permissionState.status
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when (result) {
            is PermissionStatus.Denied -> {
                if (result.shouldShowRationale) {
                    Text(
                        text = "I need permission to access audio on this device",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Looks like you permanently denied permission. Please provide in Settings",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            PermissionStatus.Granted -> {
                SaveableLaunchedEffect(Unit) {
                    permissionGranted()
                }
                MusicListContent(
                    isLoading = isLoading,
                    songs = songs,
                    lazyListState = lazyListState,
                    playMusic = playMusic,
                    sendUiEvent = sendUiEvent
                )
            }
        }
    }
}

@Composable
fun AnimatedBottomPlayer(
    state: MusicListViewState,
    isShown: Boolean,
    sendUiEvent: (MusicListUiEvent) -> Unit,
) {
    AnimatedVisibility(
        visible = isShown,
        enter = slideIn(initialOffset = { IntOffset(0, 100) }),
        exit = slideOut(targetOffset = { IntOffset(0, 200) }),
    ) {
        BottomPlayer(
            progress = state.progress,
            songName = state.currentSong?.name ?: "Error",
            artist = state.currentSong?.artist ?: "Error",
            isPlaying = state.isPlaying,
            onPlayPauseClicked = {
                sendUiEvent(MusicListUiEvent.PlayPause)
            },
            onRewindClicked = {
                sendUiEvent(MusicListUiEvent.Rewind)
            },
            onFastForwardClicked = {
                sendUiEvent(MusicListUiEvent.FastForward)
            },
            onPlayPreviousClicked = {
                sendUiEvent(MusicListUiEvent.PlayPreviousItem)
            },
            onPlayNextClicked = {
                sendUiEvent(MusicListUiEvent.PlayNextItem)
            }
        )
    }
}

@Composable
fun AnimatedFAB(
    isShown: Boolean,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isShown,
        enter = slideIn(initialOffset = { IntOffset(100, 0) }),
        exit = slideOut(targetOffset = { IntOffset(200, 0) }),
        modifier = modifier
    ) {
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
    }
}

@Composable
fun MusicListContent(
    isLoading: Boolean,
    songs: List<Song>,
    lazyListState: LazyListState,
    playMusic: (Song, Int) -> Unit,
    sendUiEvent: (MusicListUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isLoading,
        label = "Loading Animation",
        modifier = modifier
    ) {
        if (it) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            MusicList(
                songs = songs,
                lazyListState = lazyListState,
                playMusic = playMusic,
                sendUiEvent = sendUiEvent
            )
        }
    }
}

@Composable
fun MusicList(
    songs: List<Song>,
    lazyListState: LazyListState,
    playMusic: (Song, Int) -> Unit,
    sendUiEvent: (MusicListUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(all = 12.dp),
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState
    ) {
        itemsIndexed(items = songs, key = { index: Int, item: Song ->
            item.name + index
        }) { index, item ->
            SongUI(
                item = item,
                modifier = Modifier.clickable {
                    playMusic(item, index)
                }
            )
        }
    }
}

@Composable
fun SongUI(
    item: Song,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(RoundedCornerShape(12.dp))
            .padding(all = 6.dp)
    ) {
        Text(
            text = item.name,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = item.artist,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleSmall
        )
        val duration = remember {
            TimeConverter.getConvertedTime(item.duration.toLong())
        }
        Text(
            text = duration,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}