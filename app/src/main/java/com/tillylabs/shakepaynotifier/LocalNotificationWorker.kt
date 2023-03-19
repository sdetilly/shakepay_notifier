package com.tillylabs.shakepaynotifier

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class LocalNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        val work = PeriodicWorkRequest.Builder(PeriodicNotificationWorker::class.java, 24, TimeUnit.HOURS).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(Const.PERIODIC_WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, work)
        return Result.success()
    }
}
