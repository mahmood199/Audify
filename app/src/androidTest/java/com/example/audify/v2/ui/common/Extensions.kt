package com.example.audify.v2.ui.common

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.delay
import org.junit.Assert

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


@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.waitForText(
    text: String,
    timeoutMillis: Long = 5000
) {
    waitUntilAtLeastOneExists(hasText(text), timeoutMillis = timeoutMillis)
}

@OptIn(ExperimentalTestApi::class)
fun ComposeTestRule.sleep(
    timeoutMillis: Long
) {
    @Suppress("SwallowedException")
    try {
        waitUntilAtLeastOneExists(hasText("NeverFound!"), timeoutMillis = timeoutMillis)
    } catch (t: Throwable) {
        // swallow this exception
    }
}

fun ComposeTestRule.textIsDisplayed(
    text: String,
    expectedOccurrences: Int = 1
) {
    if (expectedOccurrences == 1) {
        onNodeWithText(text).assertIsDisplayed()
    } else {
        Assert.assertEquals(
            onAllNodesWithText(text).fetchSemanticsNodes().size,
            expectedOccurrences
        )
    }
}

fun ComposeTestRule.textDoesNotExist(
    text: String
) {
    onNodeWithText(text).assertDoesNotExist()
}

fun ComposeTestRule.textIsDisplayedAtLeastOnce(
    text: String,
    minOccurences: Int = 1
) {
    Assert.assertEquals(onAllNodesWithText(text).fetchSemanticsNodes().size, minOccurences)
}

fun ComposeTestRule.pressButton(text: String) {
    onNodeWithText(text).performClick()
}

fun ComposeTestRule.replaceText(inputLabel: String, text: String) {
    onNodeWithText(inputLabel).performTextClearance()
    onNodeWithText(inputLabel).performTextInput(text)
}

fun hasClickLabel(label: String) = SemanticsMatcher("Clickable action with label: $label") {
    it.config.getOrNull(
        SemanticsActions.OnClick
    )?.label == label
}