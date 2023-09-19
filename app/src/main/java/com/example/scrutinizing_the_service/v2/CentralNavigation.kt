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
import com.example.scrutinizing_the_service.v2.ui.search.SearchUI

const val NAVIGATION_DURATION = 500
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
                slideOutHorizontally { -OFFSET } + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally { -OFFSET } + fadeIn()
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
                slideInHorizontally { OFFSET } + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }, popExitTransition = {
                slideOutHorizontally { -OFFSET } + fadeOut()
            }
        ) {
            SearchUI(backPress = {
                navController.popBackStack()
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