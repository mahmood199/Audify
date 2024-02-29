package com.example.audify.v2

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
import com.example.audify.v2.ui.app_icon_change.IconChangeUIContainer
import com.example.audify.v2.ui.app_icon_change.IconModel
import com.example.audify.v2.ui.audio_download.DownloadCenterUI
import com.example.audify.v2.ui.catalog.MusicListUI
import com.example.audify.v2.ui.common.SaveableLaunchedEffect
import com.example.audify.v2.ui.genre.GenreSelectionUI
import com.example.audify.v2.ui.home.landing.LandingPageUIRoot
import com.example.audify.v2.ui.notif.NotificationTestUIContainer
import com.example.audify.v2.ui.player.AudioPlayerUI
import com.example.audify.v2.ui.reminder_settings.ReminderSettingsUIContainer
import com.example.audify.v2.ui.search.history.SearchHistoryUI
import com.example.audify.v2.ui.search.result.SearchResultUI
import com.example.audify.v2.ui.settings.SettingsUIContainer
import com.example.audify.v2.ui.short_cut.ShortcutUIContainer
import com.example.data.models.Song
import kotlinx.coroutines.delay

const val OFFSET = 500

@Composable
fun NavigationCentral(
    playMusic: (Song, Int) -> Unit,
    playMusicFromRemote: (com.example.data.models.remote.saavn.Song) -> Unit,
    backPress: () -> Unit,
    iconChangeClicked: (IconModel) -> Unit,
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
            LandingPageUIRoot(
                backPress = {
                    navController.navigate(Screen.SettingsPage.name)
                },
                navigateToLocalAudioScreen = {
                    navController.navigate(Screen.AudioList.name)
                },
                navigateToSearch = {
                    navController.navigate(Screen.SearchHistory.name)
                },
                playMusicFromRemote = playMusicFromRemote,
                navigateToGenreSelection = {
                    navController.navigate(Screen.GenreSelection.name)
                },
                navigateToPlayer = {
                    navController.navigate(Screen.AudioPlayer.name)
                }
            )
        }

        composable(route = Screen.SettingsPage.name) {
            SettingsUIContainer(
                backPress = {
                    navController.popBackStack()
                },
                navigateToIconChangeScreen = {
                    navController.navigate(Screen.ChangeIcon.name)
                },
                navigateToReminderScreen = {
                    navController.navigate(Screen.ReminderNotification.name)
                }
            )
        }

        composable(route = Screen.ChangeIcon.name) {
            IconChangeUIContainer(
                backPress = {
                    navController.popBackStack()
                },
                iconClicked = {
                    iconChangeClicked(it)
                }
            )
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
            GenreSelectionUI(
                doneWithSelection = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.AudioDownloadListScreen.name
        ) {
            DownloadCenterUI(
                onDownloadItem = { song, index ->

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

        composable(
            route = Screen.NotificationTest.name
        ) {
            NotificationTestUIContainer()
        }

        composable(
            route = Screen.ReminderNotification.name
        ) {
            ReminderSettingsUIContainer()
        }
    }
}