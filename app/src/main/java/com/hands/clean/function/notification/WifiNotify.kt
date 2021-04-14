package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R

class WifiNotify(context: Context) : Notify(context) {
    override val channelId: String = context.getString(R.string.channel_wifi_name)
    override val channelName: String = context.getString(R.string.channel_wifi_name)

    override val contentText: String = "와이파이 ~~에 연결되었습니다."
}