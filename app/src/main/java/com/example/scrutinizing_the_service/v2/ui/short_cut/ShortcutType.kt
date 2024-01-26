package com.example.scrutinizing_the_service.v2.ui.short_cut

sealed class ShortcutType(val tag: String) {
    companion object {
        const val DEFAULT_VALUE = "Default"
    }
    data object Static: ShortcutType("static")
    data object Dynamic: ShortcutType("dynamic")
    data object Pinned: ShortcutType("pinned")
}