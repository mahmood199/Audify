package com.example.scrutinizing_the_service.v2.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.AppBar

@Composable
fun SettingsUIContainer(
    backPress: () -> Unit,
    navigateToIconChangeScreen: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsUI(
        state = state,
        backPress = backPress,
        navigateToIconChangeScreen = navigateToIconChangeScreen,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    )
}

@Composable
fun SettingsUI(
    state: SettingsViewState,
    backPress: () -> Unit,
    navigateToIconChangeScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {

        item(key = "settings_page_title") {
            AppBar(
                imageVector = Icons.Default.ArrowBack,
                title = "Settings",
                backPressAction = backPress,
                modifier = Modifier
                    .padding(top = 32.dp)
            )
        }


        item(key = "app_icon_change") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable {
                        navigateToIconChangeScreen()
                    }
            ) {
                Text(
                    text = "Change App Icon",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(weight = 1f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_app_icon_change),
                    contentDescription = "Change App Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsUI() {
    ScrutinizingTheServiceTheme {
        SettingsUI(
            state = SettingsViewState.default(),
            backPress = {},
            navigateToIconChangeScreen = {}
        )
    }
}