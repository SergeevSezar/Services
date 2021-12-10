package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyForegroundService: Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var notification = Notification()

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        showNotification()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForeGroundService: $message")
    }

    private fun showNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(notificationChannel)

         notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Warning")
            .setContentText("App not stop working")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()


        notificationManager.notify(1, notification)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
    }
}