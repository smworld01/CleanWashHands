package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R
import com.hands.clean.function.room.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import com.hands.clean.function.room.db

class GPSNotify(context: Context) : RecordNotify(context) {
    override val channelId: String = "GPS"
    override val channelName: String = context.getString(R.string.channel_gps_name)
}