package com.example.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyIntentService: IntentService(SERVICE_NAME) {

    private var notification = Notification()

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        showNotification()
        startForeground(1, notification)
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
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
            return Intent(context, MyIntentService::class.java)
        }
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val SERVICE_NAME = "service_name"
    }
}