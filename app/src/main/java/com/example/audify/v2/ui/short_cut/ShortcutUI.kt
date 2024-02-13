package com.example.audify.v2.ui.short_cut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.audify.v2.theme.AudifyTheme

@Composable
fun ShortcutUIContainer(
    parentShortcutType: ShortcutType?,
    modifier: Modifier = Modifier,
    viewModel: ShortcutViewModel = hiltViewModel()
) {

    val shortcutType by viewModel.shortcutType

    ShortcutUI(shortcutType = parentShortcutType, modifier = modifier)

}

@Composable
fun ShortcutUI(
    shortcutType: ShortcutType?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val text = remember(shortcutType) {
            when (shortcutType) {
                ShortcutType.Dynamic -> shortcutType.tag
                ShortcutType.Pinned -> shortcutType.tag
                ShortcutType.Static -> shortcutType.tag
                null -> "Null tag"
            }
        }

        Text(text = text)
    }
}


@Preview
@Composable
fun ShortcutUIPreview() {
    AudifyTheme {
        ShortcutUI(ShortcutType.Dynamic)
    }
}