package com.example.scrutinizing_the_service.v2.ui.notif

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.ServiceApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class NotificationTestViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application = application) {

    companion object {
        const val NOTIFICATION_TEST_CHANNEL_ID = "notification_test_channel_id"
        const val NOTIFICATION_TEST_CHANNEL_DESCRIPTION = "Showing Progress for downloading item"
    }

    private val _state = MutableStateFlow(NotificationTestViewState.default())
    val state = _state.asStateFlow()

    private val notificationManager by lazy {
        NotificationManagerCompat.from(getApplication())
    }


    private val stateLock = Mutex()

    fun doSomething() {

    }

    @SuppressLint("MissingPermission")
    fun increaseNotification() {
        val serviceChannel = NotificationChannel(
            NOTIFICATION_TEST_CHANNEL_ID,
            NOTIFICATION_TEST_CHANNEL_DESCRIPTION,
            NotificationManager.IMPORTANCE_LOW
        )
        val manager =
            getApplication<ServiceApplication>().getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)


        val currentSize = state.value.notifications.size
        val newNotificationTitle = "Notification Title #${currentSize + 1}"
        val newNotificationDescription = "Notification Description #${currentSize + 1}"
        val progress = 0f

        val notificationModel = NotificationModel(
            title = newNotificationTitle,
            description = newNotificationDescription,
            progress = progress
        )

        val notification =
            NotificationCompat.Builder(getApplication(), NOTIFICATION_TEST_CHANNEL_ID)
                .setContentTitle(notificationModel.title)
                .setContentText(notificationModel.description)
                .setSmallIcon(R.drawable.ic_file_download_notification)
                .setProgress(100, progress.toInt(), false)
                .build()

        notificationManager.notify(currentSize, notification)
        viewModelScope.launch {
            state.value.notifications.add(Pair(currentSize, notificationModel))
            updateState {
                copy(notifications = state.value.notifications)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun increaseNotificationProgress(item: NotificationModel, index: Int) {
        val progress = state.value.notifications[index].second.progress.times(100) + 10
        val notificationModel = state.value.notifications[index].second
        val updatedModel = notificationModel.copy(progress = (progress * 1f) / 100)
        viewModelScope.launch {
            updateState {
                state.value.notifications[index] = Pair(index, updatedModel)
                copy(notifications = state.value.notifications)
            }
        }
        val notification =
            NotificationCompat.Builder(getApplication(), NOTIFICATION_TEST_CHANNEL_ID)
                .setContentTitle(item.title)
                .setContentText(item.description)
                .setSmallIcon(R.drawable.ic_file_download_notification)
                .setProgress(100, progress.toInt(), false)
                .build()
        notificationManager.notify(index, notification)
    }

    @SuppressLint("MissingPermission")
    fun decreaseNotificationProgress(item: NotificationModel, index: Int) {
        val progress = state.value.notifications[index].second.progress.times(100) - 10
        val notificationModel = state.value.notifications[index].second
        val updatedModel = notificationModel.copy(progress = (progress * 1f) / 100)
        viewModelScope.launch {
            updateState {
                state.value.notifications[index] = Pair(index, updatedModel)
                copy(notifications = state.value.notifications)
            }
        }
        val notification =
            NotificationCompat.Builder(getApplication(), NOTIFICATION_TEST_CHANNEL_ID)
                .setContentTitle(item.title)
                .setContentText(item.description)
                .setSmallIcon(R.drawable.ic_file_download_notification)
                .setProgress(100, progress.toInt(), false)
                .build()
        notificationManager.notify(index, notification)
    }

    fun removeNotification(item: NotificationModel, index: Int) {
        notificationManager.cancel(index)
        viewModelScope.launch {
            updateState {
                val discardNotification = state.value.notifications.filter { it.second == item }
                if (discardNotification.isNotEmpty()) {
                    state.value.notifications.remove(discardNotification.first())
                }
                copy(notifications = state.value.notifications)
            }
        }
    }

    private suspend fun updateState(updater: NotificationTestViewState.() -> NotificationTestViewState) {
        stateLock.withLock {
            _state.value = _state.value.updater()
        }
    }
}
