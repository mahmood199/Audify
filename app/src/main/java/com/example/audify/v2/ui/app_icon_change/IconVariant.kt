package com.example.audify.v2.ui.app_icon_change

sealed class IconVariant(val name: String) {
    data object Variant0: IconVariant("Simple")
    data object Variant1: IconVariant("Simple")
    data object Variant2: IconVariant("Gradient")
    data object Variant3: IconVariant("Moscow")
    data object Variant4: IconVariant("Stroke Gradient")
    data object Variant5: IconVariant("Bold Black")
    data object Variant6: IconVariant("Stroke Bold")
}