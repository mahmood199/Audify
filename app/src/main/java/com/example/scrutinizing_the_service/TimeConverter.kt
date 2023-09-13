package com.example.scrutinizing_the_service

object TimeConverter {

    fun getConvertedTime(seconds : Long) : String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
        val formattedSeconds = if (remainingSeconds < 10) "0$remainingSeconds" else "$remainingSeconds"

        return "$formattedMinutes:$formattedSeconds"
    }

}