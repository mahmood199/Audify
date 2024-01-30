package com.example.audify.v2.ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

fun <T> LazyListScope.HorizontalGridHackUI(
    rows: Int,
    items: List<T>,
    verticalPadding: Dp,
    modifier: Modifier = Modifier,
    parentFillPercentage: Float = 0.9f,
    content: @Composable (T) -> Unit,
) {
    items.chunked(rows).forEachIndexed { index, chuckedItems ->
        item(key = index) {
            Column(
                modifier = modifier
                    .fillParentMaxWidth(fraction = parentFillPercentage)
                    .fillParentMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(verticalPadding)
            ) {
                chuckedItems.forEach {
                    content(it)
                }
            }
        }
    }
}