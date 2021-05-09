package com.hands.clean.activity.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hands.clean.R
import com.hands.clean.function.notification.factory.WashNotification
import com.hands.clean.function.room.entrys.WifiEntry

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initLayout()
    }

    private fun initLayout() {
        val buttonSendNotification : Button = findViewById(R.id.buttonSendNotification)
        val buttonChangeNotificationChannel : Button = findViewById(R.id.buttonChangeNotificationChannel)

        buttonSendNotification.setOnClickListener {
            WashNotification(applicationContext, WifiEntry(0, "1", "2", false)).create().onNotify()
        }

        buttonChangeNotificationChannel.setOnClickListener {
            WashNotification(applicationContext, WifiEntry(0, "", "", false))
        }
    }
}