package com.example.scrutinizing_the_service.v2.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.v2.common.AppBar

@Composable
fun SearchUI(
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                AppBar(
                    imageVector = Icons.Default.ArrowBack,
                    title = "Search",
                    backPressAction = backPress,
                )

                OutlinedTextField(
                    value = state.query, onValueChange = {
                        viewModel.updateSearchQuery(it)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Leading Icon"
                        )
                    },
                    trailingIcon = {
                        if (state.query.isNotBlank()) {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.LightGray)
                                    .clickable {
                                        viewModel.updateSearchQuery("")
                                    },
                                tint = Color.Black,
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Clear Search Trailing Icon"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(horizontal = 12.dp),
        ) {
            for (i in 1..6) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Header${i}", style = MaterialTheme.typography.titleLarge)
                    Text("Description${i}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }

}

@Preview
@Composable
fun SearchUIPreview() {
    SearchUI(backPress = {

    })
}
