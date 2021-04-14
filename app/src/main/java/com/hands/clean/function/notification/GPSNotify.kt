package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R

class GPSNotify(context: Context) : Notify(context) {
    override val channelId: String = context.getString(R.string.channel_gps_name)
    override val channelName: String = context.getString(R.string.channel_gps_name)

    override val contentText: String = "~~에 있습니다."
}