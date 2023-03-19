package com.tillylabs.shakepaynotifier

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class LocalNotificationService(private val context: Context) {

    companion object {
        const val LOCAL_NOTIFICATIONS_TAG = "local_notification"
    }

    fun scheduleNotifications(): String {
        val timeBeforeOneAm = TimeUtils.timeUntilOneAm()
        Log.d("SHAKEPAYNOTIF", "Notification in ${timeBeforeOneAm.inWholeMinutes}")
        val work =
            OneTimeWorkRequestBuilder<LocalNotificationWorker>()
                .setInitialDelay(timeBeforeOneAm.inWholeMilliseconds, TimeUnit.MILLISECONDS)
                .addTag(LOCAL_NOTIFICATIONS_TAG)
                .build()
        WorkManager.getInstance(context).enqueueUniqueWork(Const.SINGLE_WORK_TAG, ExistingWorkPolicy.KEEP, work)
         return work.id.toString()
    }
}
