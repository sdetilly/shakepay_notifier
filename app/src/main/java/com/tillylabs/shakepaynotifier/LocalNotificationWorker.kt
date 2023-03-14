package com.tillylabs.shakepaynotifier

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class LocalNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {
    companion object {
        const val KEY_TITLE = "title"
        const val KEY_MESSAGE = "message"
    }

    override fun doWork(): Result {
        val title = inputData.getString(KEY_TITLE)
        val message = inputData.getString(KEY_MESSAGE)
        val openShakepayIntent = context.packageManager.getLaunchIntentForPackage("com.shaketh")
        val notificationId = Random.nextInt()
        val contentIntent = PendingIntent.getActivity(context, 0, openShakepayIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(context, Const.LOCAL_NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_warning)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return Result.failure()
        }
        val channel = NotificationChannel(
            Const.LOCAL_NOTIFICATIONS_CHANNEL_ID,
            "Shakepay notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context).apply {
            createNotificationChannel(channel)
            notify(notificationId, builder.build())
        }
        return Result.success()
    }
}
