package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R

class WifiNotify(context: Context) : Notify(context) {
    override val channelId: String = "WiFi"
    override val channelName: String = context.getString(R.string.channel_wifi_name)
}