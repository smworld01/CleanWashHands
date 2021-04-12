package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.R

class BluetoothNotify(context: Context) : Notify(context) {
    override val channelId: String = "bluetooth"
    override val channelName: String = context.getString(R.string.channel_bluetooth_name)

    override val contentText: String = "블루투스 ~~에 연결되었습니다."
}