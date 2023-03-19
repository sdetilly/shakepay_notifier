package com.tillylabs.shakepaynotifier

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class PeriodicNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        val openShakepayIntent = context.packageManager.getLaunchIntentForPackage("com.shaketh")
        val notificationId = Random.nextInt()
        val contentIntent = PendingIntent.getActivity(context, 0, openShakepayIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(context, Const.LOCAL_NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_warning)
            .setContentTitle(context.getString(R.string.shake_notifier))
            .setContentText(context.getString(R.string.remember_to_shake_today))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return Result.failure()
        }
        val channel = NotificationChannel(
            Const.LOCAL_NOTIFICATIONS_CHANNEL_ID,
            "Shake notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context).apply {
            createNotificationChannel(channel)
            notify(notificationId, builder.build())
        }
        return Result.success()
    }
}
