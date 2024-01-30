package com.example.audify.v2.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.example.audify.v2.ui.app_icon_change.IconVariant

class LauncherIconManager {

    companion object {
        const val ALIAS_0 = "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivity"
        const val ALIAS_1 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias1"
        const val ALIAS_2 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias2"
        const val ALIAS_3 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias3"
        const val ALIAS_4 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias4"
        const val ALIAS_5 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias5"
        const val ALIAS_6 =
            "com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivityAlias6"
    }

    private fun createComponentName(context: Context, aliasName: String): ComponentName {
        return ComponentName(context, aliasName)
    }

    fun setCurrentIcon(
        context: Context,
        iconVariant: IconVariant,
    ) {
        enableVariant(context = context, iconVariant = iconVariant)
        disableVariants(context = context, iconVariant = iconVariant)
    }

    private fun enableVariant(
        context: Context,
        iconVariant: IconVariant
    ) {
        val component = when (iconVariant) {
            IconVariant.Variant0 -> ALIAS_0
            IconVariant.Variant1 -> ALIAS_1
            IconVariant.Variant2 -> ALIAS_2
            IconVariant.Variant3 -> ALIAS_3
            IconVariant.Variant4 -> ALIAS_4
            IconVariant.Variant5 -> ALIAS_5
            IconVariant.Variant6 -> ALIAS_6
        }

        enableIcon(context, component)
    }

    private fun disableVariants(context: Context, iconVariant: IconVariant) {
        val variants = listOf(
            Pair(IconVariant.Variant0, ALIAS_0),
            Pair(IconVariant.Variant1, ALIAS_1),
            Pair(IconVariant.Variant2, ALIAS_2),
            Pair(IconVariant.Variant3, ALIAS_3),
            Pair(IconVariant.Variant4, ALIAS_4),
            Pair(IconVariant.Variant5, ALIAS_5),
            Pair(IconVariant.Variant6, ALIAS_6),
        )

        variants.forEach {
            if (it.first != iconVariant) {
                disableIcon(context = context, it.second)
            }
        }

    }

    private fun enableIcon(
        context: Context,
        aliasName: String
    ) {
        Log.d("AliasNameEnable", aliasName)
        context.packageManager.setComponentEnabledSetting(
            createComponentName(context, aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableIcon(
        context: Context,
        aliasName: String
    ) {
        Log.d("AliasNameDisable", aliasName)
        context.packageManager.setComponentEnabledSetting(
            createComponentName(context, aliasName),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}