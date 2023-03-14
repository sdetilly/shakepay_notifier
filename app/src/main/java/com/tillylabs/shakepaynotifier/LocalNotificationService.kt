package com.tillylabs.shakepaynotifier

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.util.concurrent.TimeUnit

class LocalNotificationService(private val context: Context) {

    companion object {
        const val LOCAL_NOTIFICATIONS_TAG = "local_notification"
        private val TIMEZONE = TimeZone.currentSystemDefault()
    }

    fun scheduleNotifications(): String {
        val now = Clock.System.now().toLocalDateTime(TIMEZONE)
        val oneAm = LocalDateTime(now.year, now.month.value, now.dayOfMonth, 1, 0, 0)
        val nextOneAm =
            if (now > oneAm) oneAm.toInstant(TIMEZONE).plus(1, DateTimeUnit.DAY, TIMEZONE).toLocalDateTime(TIMEZONE)
        else oneAm

        val timeBeforeOneAm = nextOneAm.toInstant(TIMEZONE).minus(now.toInstant(TIMEZONE))
        Log.d("SHAKEPAYNOTIF", "Notification in ${timeBeforeOneAm.inWholeMinutes}")
        val notificationData = Data.Builder()
            .putString(LocalNotificationWorker.KEY_TITLE, "Title")
            .putString(LocalNotificationWorker.KEY_MESSAGE, "Message")
            .build()
        val work =
            OneTimeWorkRequestBuilder<LocalNotificationWorker>()
                .setInitialDelay(timeBeforeOneAm.inWholeMilliseconds, TimeUnit.MILLISECONDS)
                .setInputData(notificationData)
                .addTag(LOCAL_NOTIFICATIONS_TAG)
                .build()
        WorkManager.getInstance(context).enqueue(work)
         return work.id.toString()
    }
}
