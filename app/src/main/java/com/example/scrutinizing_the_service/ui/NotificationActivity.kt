package com.example.scrutinizing_the_service.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.databinding.ActivityNotificationBinding
import com.example.scrutinizing_the_service.notifs.OreoMultiNotificationBuilder
import com.example.scrutinizing_the_service.notifs.OreoNotificationBuilder
import com.example.scrutinizing_the_service.notifs.PreOreoNotificationBuilder
import com.example.scrutinizing_the_service.notifs.SafeNotificationBuilder

class NotificationActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_1_ID = "CHANNEL_1_ID"
        const val CHANNEL_2_ID = "CHANNEL_2_ID"
    }

    private val binding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    private val safeNotificationBuilder by lazy {
        SafeNotificationBuilder(
            OreoMultiNotificationBuilder(this),
            PreOreoNotificationBuilder(this)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding) {
            btnCreateChannel.setOnClickListener {
                safeNotificationBuilder.createChannel(this@NotificationActivity)
            }

            btnShowNotif.setOnClickListener {
                safeNotificationBuilder.createNotification(this@NotificationActivity)
            }
        }
    }


}