package com.example.audify.v2.ui.common

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import kotlinx.coroutines.delay

suspend fun ComposeContentTestRule.clickAllNodesWithText(
    text: String,
    delayBetweenClicks: Long = 1000L
) {
    val allNodes = onAllNodesWithText(text = text)
    allNodes.fetchSemanticsNodes().forEachIndexed { index, semanticsNode ->
        allNodes[index].performClick()
        delay(delayBetweenClicks)
    }
}

suspend fun ComposeContentTestRule.scrollAllNodesWithText(
    text: String,
    delayBetweenClicks: Long = 1000L
) {
    val allNodes = onAllNodesWithText(text = text)
    allNodes.fetchSemanticsNodes().forEachIndexed { index, semanticsNode ->
        allNodes[index].performScrollTo()
        delay(delayBetweenClicks)
    }
}