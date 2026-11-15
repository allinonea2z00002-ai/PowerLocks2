package com.example.powerlocks

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder

class KeepAliveService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel("powerlock", "PowerLock", NotificationManager.IMPORTANCE_LOW)
        )
        val n = Notification.Builder(this, "powerlock")
            .setContentTitle("PowerLock চলছে")
            .setContentText("পাওয়ার মেনু সুরক্ষিত")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setOngoing(true)
            .build()
        startForeground(1, n)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
