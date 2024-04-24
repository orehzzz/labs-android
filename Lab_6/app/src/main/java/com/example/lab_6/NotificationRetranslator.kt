package com.example.lab_6

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

// Constants for notification
const val notificationID = 100
const val channelID = "channel1"

// BroadcastReceiver for handling notifications
class NotificationRetranslator : BroadcastReceiver() {

    // Method called when the broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        // Build the notification using NotificationCompat.Builder
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.notification_clock)
            .setContentTitle("Reminder") // Set title from intent
            .setContentText(intent.getStringExtra("title")) // Set content text from intent
            .setContentIntent(intent.getParcelableExtra("intent")) // intent to activate when click on notification
            .setAutoCancel(true)
            .build()

        // Get the NotificationManager service
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification using the manager
        manager.notify(notificationID, notification)
    }
}
