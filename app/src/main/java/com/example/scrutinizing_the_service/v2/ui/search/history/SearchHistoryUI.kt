package com.example.scrutinizing_the_service.v2.ui.search.history

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.data.models.local.RecentSearch
import com.example.scrutinizing_the_service.v2.ui.common.AppBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchHistoryUI(
    backPress: () -> Unit,
    navigateToSearchResult: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchHistoryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    var isEditMode by remember {
        mutableStateOf(false)
    }

    val searchQuery by viewModel.uiQuery.collectAsState("")

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var openAlertDialog by remember { mutableStateOf(false) }

    val controller = LocalSoftwareKeyboardController.current

    val searches by viewModel.books.collectAsState(emptyList())

    AlertDialogWrapper(
        openAlertDialog = openAlertDialog,
        hideDialog = {
            openAlertDialog = false
        }
    )

    LaunchedEffect(Unit) {
        controller?.show()
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                AppBar(
                    imageVector = Icons.Default.ArrowBack,
                    title = "Search",
                    backPressAction = backPress,
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        viewModel.updateSearchQuery(it)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Leading Icon"
                        )
                    },
                    maxLines = 1,
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.addToSearchHistory(searchQuery)
                            focusManager.clearFocus()
                            navigateToSearchResult(searchQuery)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        )
                        .onFocusChanged {
                            isEditMode = it.isFocused
                        }
                        .focusRequester(focusRequester)
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (isEditMode) {
                    focusManager.clearFocus()
                }
            }
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
            RecentSearchList(
                searches = searches,
                onSearchSelected = { selectedQuery ->
                    viewModel.updateSearchQuery(selectedQuery)
                    viewModel.addToSearchHistory(selectedQuery)
                    focusManager.clearFocus()
                    navigateToSearchResult(selectedQuery)
                },
                removeSearch = {
                    viewModel.deleteSearch(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogWrapper(
    openAlertDialog: Boolean,
    hideDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (openAlertDialog) {
        AlertDialog(
            modifier = modifier
                .fillMaxWidth(),
            onDismissRequest = {
                hideDialog()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Are you sure to this from recent searches")
                Text("This action will remove this search from your search history")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Button(
                        onClick = {
                            hideDialog()
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                    Button(
                        onClick = {
                            hideDialog()
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun RecentSearchList(
    searches: List<RecentSearch>,
    onSearchSelected: (String) -> Unit,
    removeSearch: (RecentSearch) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 12.dp,
            horizontal = 16.dp
        ),
        modifier = Modifier
    ) {
        searches.forEachIndexed { index, search ->
            item(key = "${search.query}${search.timeStamp}") {
                RecentlySearchedItem(
                    recentSearch = search,
                    index = index,
                    onSearchSelected = onSearchSelected,
                    removeSearch = removeSearch
                )
            }
        }
    }
}

@Composable
fun RecentlySearchedItem(
    recentSearch: RecentSearch,
    index: Int,
    onSearchSelected: (String) -> Unit,
    removeSearch: (RecentSearch) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSearchSelected(recentSearch.query)
            }
            .padding(vertical = 6.dp)
            .animateContentSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_redirect),
            contentDescription = "Redirect recent $index",
            modifier = Modifier.clickable {

            },
            tint = Color.White
        )
        Text(text = recentSearch.query, modifier = Modifier
            .weight(1f)
            .clickable {
                onSearchSelected(recentSearch.query)
            })
        Icon(
            imageVector = Icons.Default.Close, contentDescription = "Remove recent $index",
            modifier = Modifier.clickable {
                removeSearch(recentSearch)
            }
        )
    }
}

@Preview
@Composable
fun SearchUIPreview() {
    SearchHistoryUI(backPress = {

    }, navigateToSearchResult = {

    })
}
