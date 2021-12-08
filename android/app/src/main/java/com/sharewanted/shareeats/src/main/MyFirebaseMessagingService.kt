package com.sharewanted.shareeats.src.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.userlogin.LoginActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("싸피", "onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.data.isNotEmpty()){
            Log.i("바디: ", remoteMessage.data["body"].toString())
            Log.i("타이틀: ", remoteMessage.data["title"].toString())
            sendNotification(remoteMessage)
            sendNotification(remoteMessage)
        } else {
            Log.i("수신에러: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("data값: ", remoteMessage.data.toString())
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.firebase_notification_channel_id)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.data["body"].toString())
            .setContentText(remoteMessage.data["title"].toString())
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, getString(R.string.firebase_notification_channel_id), NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(uniId, notificationBuilder.build())
    }
}