package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.common.AppBar
import com.example.scrutinizing_the_service.v2.ui.common.SaveableLaunchedEffect

@Composable
fun IconChangeUIContainer(
    backPress: () -> Unit,
    iconClicked: (IconModel) -> Unit,
    viewModel: IconChangeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    IconChangeUI(
        state = state,
        backPress = backPress,
        initViewState = {
            viewModel.addIconsToState()
        },
        iconClicked = {
            iconClicked(it)
        }
    )
}

@Composable
fun IconChangeUI(
    state: IconChangeViewState,
    backPress: () -> Unit,
    initViewState: (Context) -> Unit,
    iconClicked: (IconModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    SaveableLaunchedEffect(Unit) {
        initViewState(context)
    }

    var selectedIcon by remember {
        mutableStateOf<IconModel?>(null)
    }

    var showDialogForRestart by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .fillMaxSize()
                .background(Color.Gray),
        ) {

            item(
                key = "icon_change_page_title",
                span = {
                    GridItemSpan(3)
                }
            ) {
                AppBar(
                    imageVector = Icons.Default.ArrowBack,
                    title = "Change App Icon",
                    backPressAction = backPress,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }

            items(
                count = state.icons.count(),
                span = {
                    GridItemSpan(1)
                },
                key = {
                    "${state.icons.elementAt(it).resourceId + it}"
                }
            ) {
                val item = state.icons.elementAt(it)
                AppIcon(
                    drawableResource = item,
                    isSelected = selectedIcon == item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (it % 3 == 0) Modifier.padding(start = 12.dp)
                            else if (it % 3 == 2) Modifier.padding(end = 12.dp)
                            else Modifier.padding(horizontal = 6.dp)
                        )
                ) {
                    selectedIcon = item
                }
            }
        }

        Button(
            onClick = {
                if (selectedIcon != null) {
                    showDialogForRestart = true
                } else {
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            Text("Confirm Selection")
        }


        if (showDialogForRestart) {
            Dialog(
                onDismissRequest = {
                    showDialogForRestart = false
                }) {
                RestartDialogContent(
                    onRestartClicked = {
                        iconClicked(selectedIcon!!)
                    }
                )
            }
        }
    }
}

@Composable
fun RestartDialogContent(
    onRestartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .background(Color.White)
            .padding(12.dp)
    ) {
        Text(
            text = "Alert",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "App will close and you'll have to open it again for the changes to take place",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRestartClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Proceed")
        }
    }
}

@Composable
fun AppIcon(
    drawableResource: IconModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = drawableResource.resourceId),
                contentDescription = "$drawableResource",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                tint = Color.Unspecified,
            )

            Text(text = drawableResource.iconVariant.name)
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
            )
        }
    }
}


@Preview
@Composable
fun IconChangeUIPreview() {
    ScrutinizingTheServiceTheme {
        val context = LocalContext.current
        val icons = listOf(
            R.mipmap.ic_app_launcher_v1,
            R.mipmap.ic_app_launcher_v2,
            R.mipmap.ic_app_launcher_v3,
            R.mipmap.ic_app_launcher_v4,
            R.mipmap.ic_app_launcher_v5,
            R.mipmap.ic_app_launcher_v6,
        )
        IconChangeUI(
            state = IconChangeViewState.default()
                .copy(icons = mutableListOf<IconModel>().asSequence()),
            backPress = {

            }, {

            }, {

            }
        )
    }
}