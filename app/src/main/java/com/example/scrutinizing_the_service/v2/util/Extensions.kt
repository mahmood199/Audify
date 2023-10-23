package com.example.scrutinizing_the_service.v2.util

import android.content.Context
import com.example.scrutinizing_the_service.TimeConverter

fun calculateProgressValue(currentProgress: Long, duration: Long): Pair<Float, String> {
    val progress = if (currentProgress > 0) currentProgress.toFloat() / duration.toFloat()
    else 0f
    return Pair(
        first = progress,
        second = TimeConverter.getConvertedTime(currentProgress / 1000),
    )
}

fun Int.px(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun Float.px(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun String.convertToAbbreviatedViews(): String {
    return try {
        val views = this.toLong()

        val abbreviatedViews = when {
            views < 1_000 -> {
                views.toString()
            }
            views < 1_00_000 -> {
                String.format("%.1fK", views / 1_000.0)
            }
            views < 1_000_000 -> {
                (views / 1_000).toString() + "K"
            }
            else -> {
                (views / 1_000_000).toString() + "M"
            }
        }
        abbreviatedViews
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}