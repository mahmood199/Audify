package com.example.scrutinizing_the_service.v2.ui.app_icon_change

import androidx.compose.ui.graphics.Color

data class IconModel(
    val resourceId: Int,
    val backgroundColor: Color,
    val iconVariant: IconVariant
)