package com.example.scrutinizing_the_service.v2

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scrutinizing_the_service.compose_utils.SaveableLaunchedEffect
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import com.example.scrutinizing_the_service.v2.ui.audio_download.AudioDownloadUI
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListUI
import com.example.scrutinizing_the_service.v2.ui.genre.GenreSelectionUI
import com.example.scrutinizing_the_service.v2.ui.home.landing.LandingPageUI
import com.example.scrutinizing_the_service.v2.ui.player.AudioPlayerUI
import com.example.scrutinizing_the_service.v2.ui.search.history.SearchHistoryUI
import com.example.scrutinizing_the_service.v2.ui.search.result.SearchResultUI
import com.example.scrutinizing_the_service.v2.ui.settings.SettingsUI
import com.example.scrutinizing_the_service.v2.ui.short_cut.ShortcutType
import com.example.scrutinizing_the_service.v2.ui.short_cut.ShortcutUIContainer
import kotlinx.coroutines.delay

const val OFFSET = 500

@Composable
fun NavigationCentral(
    playMusic: (Song, Int) -> Unit,
    playMusicFromRemote: (RecentlyPlayed) -> Unit,
    backPress: () -> Unit,
    onDownloadSong: (com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val shortcutType by viewModel.shortcutType

    SaveableLaunchedEffect(Unit) {
        delay(2000)
        when (shortcutType) {
            null -> Unit
            else -> {
                navController.navigate(route = Screen.ShortcutScreen.name)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.name,
        modifier = modifier
    ) {
        composable(route = Screen.LandingPage.name) {
            LandingPageUI(
                backPress = {
                    navController.navigate(Screen.SettingsPage.name)
                },
                redirectToLocalAudioScreen = {
                    navController.navigate(Screen.AudioList.name)
                },
                navigateToSearch = {
                    navController.navigate(Screen.AudioDownloadListScreen.name)
                },
                playMusicFromRemote = playMusicFromRemote,
                redirectToGenreSelection = {
                    navController.navigate(Screen.GenreSelection.name)
                }
            )
        }

        composable(route = Screen.SettingsPage.name) {
            SettingsUI()
        }

        composable(
            route = Screen.AudioList.name,
            exitTransition = {
                slideOutHorizontally { -OFFSET }
            },
            popEnterTransition = {
                slideInHorizontally { -OFFSET }
            }
        ) {
            MusicListUI(
                playMusic = playMusic,
                backPress = {
                    navController.popBackStack()
                },
                navigateToSearch = {
                    navController.navigate(Screen.SearchHistory.name)
                },
                navigateToPlayer = {
                    navController.navigate(Screen.AudioPlayer.name)
                }
            )
        }

        composable(
            route = Screen.SearchHistory.name,
            enterTransition = {
                slideInHorizontally { OFFSET }
            },
            exitTransition = {
                slideOutHorizontally { OFFSET }
            }, popExitTransition = {
                slideOutHorizontally { OFFSET }
            }
        ) {
            SearchHistoryUI(
                backPress = {
                    navController.popBackStack()
                }, navigateToSearchResult = {
                    navController.navigate(Screen.SearchResult.name.plus("/${it}"))
                }
            )
        }

        composable(
            route = Screen.SearchResult.name + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            }),
            enterTransition = {
                slideInHorizontally { OFFSET }
            },
            exitTransition = {
                slideOutHorizontally { OFFSET }
            }, popExitTransition = {
                slideOutHorizontally { OFFSET }
            }
        ) { navBackStackEntry ->
            val query = navBackStackEntry.arguments?.getString("query") ?: ""
            SearchResultUI(
                query = query,
                backPress = {
                    navController.popBackStack()
                },
                navigateToPlayer = {
                    navController.navigate(Screen.AudioPlayer.name)
                }
            )
        }

        composable(
            route = Screen.AudioPlayer.name,
        ) {
            AudioPlayerUI(
                backPress = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Main.name,
            enterTransition = {
                slideInHorizontally { OFFSET } + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }, popExitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }
        ) {

        }

        composable(
            route = Screen.GenreSelection.name
        ) {
            GenreSelectionUI()
        }

        composable(
            route = Screen.AudioDownloadListScreen.name
        ) {
            AudioDownloadUI(
                onDownloadItem = { song, index ->
                    onDownloadSong(song, index)
                }
            )
        }

        composable(
            route = Screen.ShortcutScreen.name
        ) {
            ShortcutUIContainer(
                parentShortcutType = shortcutType
            )
        }
    }
}