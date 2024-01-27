package com.example.scrutinizing_the_service.v2.ui.notif

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme

@Composable
fun NotificationTestUIContainer(
    modifier: Modifier = Modifier,
    viewModel: NotificationTestViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    NotificationTestUI(
        state = state,
        createNotification = {
            viewModel.increaseNotification()
        },
        increaseProgress = { item, index ->
            viewModel.increaseNotificationProgress(item, index)
        },
        decreaseProgress = { item, index ->
            viewModel.decreaseNotificationProgress(item, index)
        },
        removeNotification = { item, index ->
            viewModel.removeNotification(item, index)
        },
        modifier = modifier
    )

}

@Composable
fun NotificationTestUI(
    state: NotificationTestViewState,
    createNotification: () -> Unit,
    removeNotification: (NotificationModel, Int) -> Unit,
    increaseProgress: (NotificationModel, Int) -> Unit,
    decreaseProgress: (NotificationModel, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color.Red)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            items(count = state.notifications.size, key = {
                val item = state.notifications[it].second
                item.title + "$it"
            }) {
                val item = state.notifications[it].second
                NotificationUI(
                    item = item,
                    increaseProgress = {
                        increaseProgress(item, it)
                    },
                    decreaseProgress = {
                        decreaseProgress(item, it)
                    },
                    removeNotification = {
                        removeNotification(item, it)
                    }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = createNotification,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Text("Increase notification")
            }
        }
    }
}

@Composable
fun NotificationUI(
    item: NotificationModel,
    increaseProgress: () -> Unit,
    decreaseProgress: () -> Unit,
    removeNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = item.title)
        Text(text = item.description)
        LinearProgressIndicator(progress = item.progress, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth()) {
            Button({
                increaseProgress()
            }, modifier = Modifier.weight(1f)) {
                Text("Increase progress")
            }
            Button({
                decreaseProgress()
            }, modifier = Modifier.weight(1f)) {
                Text("Decrease progress")
            }
        }
        Button(
            onClick = removeNotification
        ) {
            Text("Remove this notification")
        }
    }
}

@Preview
@Composable
fun NotificationTestUIPreview() {
    ScrutinizingTheServiceTheme {
        NotificationTestUI(
            state = NotificationTestViewState.default(),
            createNotification = {

            },
            removeNotification = { item, index ->

            },
            increaseProgress = { item, index ->

            },
            decreaseProgress = { item, index ->

            },
        )
    }
}
