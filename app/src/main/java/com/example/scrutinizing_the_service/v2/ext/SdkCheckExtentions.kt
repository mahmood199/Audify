package com.example.scrutinizing_the_service.v2.ext

import android.os.Build.VERSION
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(parameter = 0)
fun isAtLeastVersion(version: Int): Boolean {
    return VERSION.SDK_INT >= version
}
