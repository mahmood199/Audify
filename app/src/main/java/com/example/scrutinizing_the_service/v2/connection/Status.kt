package com.example.scrutinizing_the_service.v2.connection

sealed class Status(val displayText: String) {
    data object Available : Status("Available")
    data object Unavailable : Status("Unavailable")
    data object Losing : Status("Losing")
    data object Lost : Status("Lost")
    data object Inactive : Status("Inactive")
}