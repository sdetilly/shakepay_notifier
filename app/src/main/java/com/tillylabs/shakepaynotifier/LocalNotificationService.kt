package com.tillylabs.shakepaynotifier

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import kotlin.time.TimeMark

class LocalNotificationService(private val context: Context) {

    companion object {
        const val LOCAL_NOTIFICATIONS_TAG = "local_notification"
    }

    /* suspend fun scheduleNotifications(notifications: List<Date>): List<String> {
        return notifications.map { notification ->
            val initialDelay = notification.scheduleAtEpoc - DateTime.now().unixMillisLong
            val notificationData = Data.Builder()
                .putString(LocalNotificationWorker.KEY_TITLE, notification.title)
                .putString(LocalNotificationWorker.KEY_MESSAGE, notification.message)
                .build()
            val work =
                OneTimeWorkRequestBuilder<LocalNotificationWorker>()
                    .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                    .setInputData(notificationData)
                    .addTag(LOCAL_NOTIFICATIONS_TAG)
                    .build()
            WorkManager.getInstance(context).enqueue(work)
            work.id.toString()
        }
    }*/
}
