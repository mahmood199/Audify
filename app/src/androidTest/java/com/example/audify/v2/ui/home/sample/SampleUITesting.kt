package com.example.audify.v2.ui.home.sample

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.audify.v2.theme.AudifyTheme
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleUITesting {

/*
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)
*/

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        Log.d("SampleUITesting", "Setup")
//        hiltRule.inject()
    }

    @Test
    fun perform(): Unit = runBlocking {
        Log.d("SampleUITesting", "perform")
        composeTestRule.setContent {
            AudifyTheme {
                BasicUI()
            }
        }
        Log.d("ComposeUITesting", "Started")


        composeTestRule.onNodeWithText("Button").performClick()

        composeTestRule.onNodeWithText("Clicked!").assertIsDisplayed()
    }

}

@Composable
fun BasicUI(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Button")
            }

            Button(onClick = {

            }) {
                Text(text = "Clicked!")
            }
        }
    }
}
