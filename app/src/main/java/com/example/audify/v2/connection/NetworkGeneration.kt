package com.example.audify.v2.connection

sealed class NetworkGeneration(val name: String) {

    data object Unknown : NetworkGeneration(name = "1G")
    data object TwoG : NetworkGeneration(name = "2G")
    data object ThreeG : NetworkGeneration(name = "3G")
    data object FourG : NetworkGeneration(name = "4G")
    data object FiveG : NetworkGeneration(name = "5G")
    data object Wifi : NetworkGeneration(name = "Wifi")
    data object Ethernet : NetworkGeneration(name = "Ethernet")
}