package com.asiman.cryptotracker.support.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.base.Constants
import com.asiman.cryptotracker.base.Constants.CHANNEL_ID
import kotlin.random.Random

fun Context.sendNotification(title: String, description: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(description)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(this)) {
        notify(Random.nextInt(), builder.build())
    }
}