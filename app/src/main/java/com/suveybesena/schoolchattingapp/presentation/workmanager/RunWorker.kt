package com.suveybesena.schoolchattingapp.presentation.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.suveybesena.schoolchattingapp.R

class RunWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {

        val builder: NotificationCompat.Builder
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "channelId"
        val channelName = "channelName"
        val channelIntroduction = "channelIntroduction"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager
            .getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelIntroduction
            notificationManager.createNotificationChannel(channel)
        }

        builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Daily run")
            .setContentText("When it comes to movement, no amount is too little. A quick, 15-minute walk around the block can even have a number of surprising health benefits.")
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
        notificationManager.notify(1, builder.build())
    }
}