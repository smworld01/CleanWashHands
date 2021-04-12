package com.hands.clean.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hands.clean.R
import com.hands.clean.function.notification.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initLayout()
    }

    private fun initLayout() {
        var i: Int = 0
        val buttonSendNotification : Button = findViewById(R.id.buttonSendNotification)
        val buttonChangeNotificationChannel : Button = findViewById(R.id.buttonChangeNotificationChannel)

        buttonSendNotification.setOnClickListener {
            BluetoothNotify(this)
                .sendNotification()
        }

        buttonChangeNotificationChannel.setOnClickListener {
            WifiNotify(this)
                .sendNotification()
        }
    }
}