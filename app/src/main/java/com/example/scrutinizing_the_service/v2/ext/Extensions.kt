package com.example.scrutinizing_the_service.v2.ext

import com.example.scrutinizing_the_service.TimeConverter

fun calculateProgressValue(currentProgress: Long, duration: Long): Pair<Float, String> {
    val progress = if (currentProgress > 0) currentProgress.toFloat() / duration.toFloat()
    else 0f
    return Pair(
        first = progress,
        second = TimeConverter.getConvertedTime(currentProgress),
    )
}