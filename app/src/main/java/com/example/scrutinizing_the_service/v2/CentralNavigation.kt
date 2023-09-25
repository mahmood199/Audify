package com.example.scrutinizing_the_service.v2

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.scrutinizing_the_service.v2.ui.search_history.SearchHistoryUI
import com.example.scrutinizing_the_service.v2.ui.search_result.SearchResultUI

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
        startDestination = Screen.AudioList.name,
        modifier = modifier.background(Color.White),
    ) {
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
                backPress = backPress,
                navigateToSearch = {
                    navController.navigate(Screen.SearchHistory.name)
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
                }
            )
        }

        composable(
            route = Screen.AudioPlayer.name,
            enterTransition = {
                slideInHorizontally { OFFSET } + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }, popExitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Cyan)
            ) {

            }
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