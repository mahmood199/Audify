package com.example.scrutinizing_the_service.v2.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

inline val <reified T : Any> LazyPagingItems<T>.isFirstLoad
    get() = loadState.refresh is LoadState.Loading && itemSnapshotList.items.isEmpty()

inline val <reified T : Any> LazyPagingItems<T>.isRefreshing
    get() = loadState.refresh is LoadState.Loading && itemSnapshotList.items.isNotEmpty()

inline val <reified T : Any> LazyPagingItems<T>.isPrepending
    get() = loadState.prepend is LoadState.Loading && itemSnapshotList.items.isNotEmpty()

inline val <reified T : Any> LazyPagingItems<T>.isAppending
    get() = loadState.append is LoadState.Loading && itemSnapshotList.items.isNotEmpty()

inline val <reified T : Any> LazyPagingItems<T>.failedToLoad
    get() = loadState.refresh is LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.failedToPrepend
    get() = loadState.prepend is LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.failedToAppend
    get() = loadState.append is LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.loadError
    get() = loadState.refresh as? LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.prependError
    get() = loadState.prepend as? LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.appendError
    get() = loadState.append as? LoadState.Error

inline val <reified T : Any> LazyPagingItems<T>.noItemsToShow
    get() = loadState.append.endOfPaginationReached && itemCount == 0

inline val <reified T : Any> LazyPagingItems<T>.isEmpty
    get() = itemCount == 0
