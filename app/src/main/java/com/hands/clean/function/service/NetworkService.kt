package com.hands.clean.function.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyType

class NetworkService: Service() {
    private var networkConnectionCheck: NetworkConnectionCheck? = null
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        throw UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("test", "start")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //  LOLLIPOP Version 이상..
            if(networkConnectionCheck==null){
                networkConnectionCheck=NetworkConnectionCheck(applicationContext, intent);
                networkConnectionCheck?.register();
            }
        }
        registerNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy();
        stopForeground(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {     //  LOLLIPOP Version 이상..
            if(networkConnectionCheck!=null) networkConnectionCheck?.unregister();
        }
    }

    private fun registerNotification() {
        val notification: Notification = NotificationCompat.Builder(applicationContext, NotifyType.NoticeService.channelId)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("WiFi와 GPS를 확인하고 있습니다.")
                .setContentText("테스트 중..")
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        startForeground(1, notification);
    }
}