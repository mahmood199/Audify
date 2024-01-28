package com.example.scrutinizing_the_service.v2.util

fun Long.bytesToKb(): Double {
    return this / 1024.0
}

fun Long.bytesToMb(): Double {
    return this / 1024.0 / 1024.0
}

fun Double.kbToBytes(): Long {
    return (this * 1024).toLong()
}

fun Double.mbToBytes(): Long {
    return (this * 1024 * 1024).toLong()
}
