package com.example.audify.v2.ui.home.landing

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.audify.v2.theme.AudifyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RunWith(AndroidJUnit4::class)
class LandingPageUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val firstCondition = hasText("Albums") and hasClickAction()
    val secondCondition = hasText("Artists") and hasClickAction()
    val thirdCondition = hasText("Songs") and hasClickAction()

    @Before
    fun setup() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun performLandingUITest(): Unit = runBlocking {
        Log.d("ComposeUITesting", "Started")

        composeTestRule.setContent {
            val state by remember {
                mutableStateOf(LandingPageViewState())
            }

            val headers = getHeaders()

            val pagerState = rememberPagerState(
                initialPage = state.userSelectedPage,
                initialPageOffsetFraction = 0f
            ) {
                headers.size
            }

            val uiController = rememberSystemUiController()

            LaunchedEffect(Unit) {
                uiController.setNavigationBarColor(Color.Transparent)
            }
            val snackBarHostState = remember { SnackbarHostState() }

            val dismissSnackBarState =
                rememberSwipeToDismissBoxState(confirmValueChange = { value ->
                    if (value != SwipeToDismissBoxValue.Settled) {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        true
                    } else {
                        false
                    }
                })

            AudifyTheme {
                LandingPageUI(
                    state = state,
                    pagerState = pagerState,
                    headers = headers,
                    snackBarHostState = snackBarHostState,
                    dismissSnackBarState = dismissSnackBarState,
                    sendMediaAction = {

                    },
                    sendUIEvent = {

                    },
                    navigateToGenreSelection = {

                    },
                    navigateToLocalAudioScreen = {

                    },
                    navigateToSearch = {

                    },
                    backPress = {

                    },
                    playMusicFromRemote = {

                    },
                    navigateToPlayer = {

                    },
                )
            }
        }

        delay(20000)

        composeTestRule.onRoot().printToLog("ComposeUITesting")
        Log.d("ComposeUITesting", "Started")

        composeTestRule.onNodeWithText("Songs").assertIsDisplayed()
    }

}