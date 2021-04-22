package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R
import com.hands.clean.function.room.WashEntry
import com.hands.clean.function.room.db
import java.text.SimpleDateFormat
import java.util.*

class WifiNotify(context: Context) : RecordNotify(context) {
    override val channelId: String = "WiFi"
    override val channelName: String = context.getString(R.string.channel_wifi_name)
}