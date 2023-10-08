package com.example.scrutinizing_the_service.v2.connection

import android.Manifest
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.scrutinizing_the_service.v2.ext.isAtLeastVersion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val telephonyManager: TelephonyManager,
) : ConnectivityObserver {

    private val _wifiNetworkState = MutableStateFlow(value = false)

    private val _mobileDataNetworkState = MutableStateFlow(value = false)

    override val connected: Boolean
        get() = _wifiNetworkState.value || _mobileDataNetworkState.value

    override val disconnected: Boolean
        get() = connected.not()

    override val networkState: Flow<Boolean> = combine(
        _wifiNetworkState,
        _mobileDataNetworkState,
    ) { isWifiConnected, isMobileDataConnected ->
        isMobileDataConnected || isWifiConnected
    }

    private val networkType by lazy {
        try {
            if (isAtLeastVersion(Build.VERSION_CODES.M)) {
                activeNetworkType()
            } else {
                activeNetworkInfoType()
            }
        } catch (e: SecurityException) {
            activeNetworkInfoType()
        }
    }

    init {
        registerWifiCallback()
        registerMobileDataCallback()
    }

    private fun registerWifiCallback() {
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) = updateWifiNetworkState(isAvailable = true)
                override fun onLost(network: Network) = updateWifiNetworkState(isAvailable = false)
                override fun onUnavailable() = updateWifiNetworkState(isAvailable = false)
            },
        )
    }

    private fun updateWifiNetworkState(isAvailable: Boolean) {
        _wifiNetworkState.value = isAvailable
    }

    private fun registerMobileDataCallback() {
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) = updateMobileNetworkState(isAvailable = true)
                override fun onLost(network: Network) = updateMobileNetworkState(isAvailable = false)
                override fun onUnavailable() = updateMobileNetworkState(isAvailable = false)
            },
        )
    }

    private fun updateMobileNetworkState(isAvailable: Boolean) {
        _mobileDataNetworkState.value = isAvailable
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    private fun activeNetworkType(): NetworkGeneration {
        val network = connectivityManager.activeNetwork ?: return NetworkGeneration.Unknown
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkGeneration.Unknown

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkGeneration.Wifi

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkGeneration.Ethernet

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> networkGeneration(
                telephonyManager.dataNetworkType()
            )

            else -> NetworkGeneration.Unknown
        }
    }

    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    private fun TelephonyManager.dataNetworkType(): Int {
        return when {
            isAtLeastVersion(Build.VERSION_CODES.N) -> dataNetworkType
            else -> networkType
        }
    }

    @Suppress("DEPRECATION")
    private fun activeNetworkInfoType(): NetworkGeneration {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return NetworkGeneration.Unknown

        return when (networkInfo.type) {
            ConnectivityManager.TYPE_WIFI -> NetworkGeneration.Wifi
            ConnectivityManager.TYPE_ETHERNET -> NetworkGeneration.Ethernet
            ConnectivityManager.TYPE_MOBILE -> networkGeneration(networkInfo.subtype)
            else -> NetworkGeneration.Unknown
        }
    }

    @Suppress("DEPRECATION")
    private fun networkGeneration(networkType: Int): NetworkGeneration {
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN,
            TelephonyManager.NETWORK_TYPE_GSM,
            -> NetworkGeneration.TwoG

            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP,
            TelephonyManager.NETWORK_TYPE_TD_SCDMA,
            -> NetworkGeneration.ThreeG

            TelephonyManager.NETWORK_TYPE_LTE,
            TelephonyManager.NETWORK_TYPE_IWLAN, 19,
            -> NetworkGeneration.FourG

            TelephonyManager.NETWORK_TYPE_NR -> NetworkGeneration.FiveG
            else -> NetworkGeneration.Unknown
        }
    }

}