package com.example.scrutinizing_the_service.v2.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WifiConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION == action) {
                val wifiState =
                    getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                when (wifiState) {
                    WifiManager.WIFI_STATE_ENABLING -> {
                        Log.d(TAG, "Connecting")
                    }

                    WifiManager.WIFI_STATE_ENABLED -> {
                        Log.d(TAG, "Connected")
                    }

                    WifiManager.WIFI_STATE_DISABLING -> {
                        Log.d(TAG, "Disconnecting")
                    }

                    WifiManager.WIFI_STATE_DISABLED -> {
                        Log.d(TAG, "Disconnected")
                    }

                    else -> {
                        Log.d(TAG, "Unknown")
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "ConnectionReceiver"
    }

}