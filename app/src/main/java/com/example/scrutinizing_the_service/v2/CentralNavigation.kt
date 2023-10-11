package com.example.scrutinizing_the_service.v2

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListUI
import com.example.scrutinizing_the_service.v2.ui.landing.LandingPageUI
import com.example.scrutinizing_the_service.v2.ui.player.AudioPlayerUI
import com.example.scrutinizing_the_service.v2.ui.search.history.SearchHistoryUI
import com.example.scrutinizing_the_service.v2.ui.search.result.SearchResultUI
import com.example.scrutinizing_the_service.v2.ui.settings.SettingsUI

const val OFFSET = 500

@Composable
fun NavigationCentral(
    playMusic: (Song, Int) -> Unit,
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.name,
        modifier = modifier.background(Color.White),
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
                    navController.navigate(Screen.SearchHistory.name)
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
    }
}