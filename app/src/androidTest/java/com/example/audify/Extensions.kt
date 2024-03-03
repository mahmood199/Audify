package com.example.audify

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection

fun mockResponse(responseBody: String, status: Int = HttpURLConnection.HTTP_OK) =
    MockResponse()
        .setResponseCode(status)
        .setBody(responseBody)

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
        assertEquals(onAllNodesWithText(text).fetchSemanticsNodes().size, expectedOccurrences)
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
    assertEquals(onAllNodesWithText(text).fetchSemanticsNodes().size, minOccurences)
}

fun ComposeTestRule.pressButton(text: String) {
    onNodeWithText(text).performClick()
}

fun ComposeTestRule.replaceText(inputLabel: String, text: String) {
    onNodeWithText(inputLabel).performTextClearance()
    onNodeWithText(inputLabel).performTextInput(text)
}

// This is just a small sample of the types of extensions
// to add as ComposeTestRule contains many operations you
// might want to wrap here