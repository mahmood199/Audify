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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListUI
import com.example.scrutinizing_the_service.v2.ui.search_history.SearchHistoryUI

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
        startDestination = ScreenName.AUDIO_LIST,
        modifier = modifier.background(Color.White),
    ) {
        composable(
            route = ScreenName.AUDIO_LIST,
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
                    navController.navigate(ScreenName.SEARCH)
                }
            )
        }
        composable(
            route = ScreenName.SEARCH,
            enterTransition = {
                slideInHorizontally { OFFSET }
            },
            exitTransition = {
                slideOutHorizontally { OFFSET }
            }, popExitTransition = {
                slideOutHorizontally { OFFSET }
            }
        ) {
            SearchHistoryUI(backPress = {
                navController.popBackStack()
            }, navigateToSearchResult = {

            })
        }
        composable(
            route = ScreenName.AUDIO_PLAYER,
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
            route = ScreenName.MAIN,
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