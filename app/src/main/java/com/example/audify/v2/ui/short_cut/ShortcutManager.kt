package com.example.audify.v2.ui.short_cut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.skydiver.audify.R

fun createShortcut(context: Context) {
    val count = ShortcutManagerCompat.getMaxShortcutCountPerActivity(context)
    val shortcut = ShortcutInfoCompat.Builder(context, "id1")
        .setShortLabel("Website")
        .setLongLabel("Open the website")
        .setIcon(IconCompat.createWithResource(context, R.drawable.ic_album_2))
        .setIntent(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.mysite.example.com/")
            ).apply {

            }
        )
        .build()

    ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
}

fun createShortcutSafely(context: Context) {
    val shortcutManager = context.getSystemService(ShortcutManager::class.java)

    if (shortcutManager!!.isRequestPinShortcutSupported) {
        // Enable the existing shortcut with the ID "my-shortcut".
        val pinShortcutInfo = ShortcutInfo.Builder(context, "my-shortcut").build()

        // Create the PendingIntent object only if your app needs to be notified
        // that the user let the shortcut be pinned. If the pinning operation fails,
        // your app isn't notified. Assume here that the app implements a method
        // called createShortcutResultIntent() that returns a broadcast intent.
        val pinnedShortcutCallbackIntent =
            shortcutManager.createShortcutResultIntent(pinShortcutInfo)

        // Configure the intent so that your app's broadcast receiver gets the
        // callback successfully. For details, see PendingIntent.getBroadcast().
        val successCallback = PendingIntent.getBroadcast(
            context, /* request code */ 0,
            pinnedShortcutCallbackIntent, PendingIntent.FLAG_IMMUTABLE
        )

        shortcutManager.requestPinShortcut(
            pinShortcutInfo,
            successCallback.intentSender
        )
    }

}