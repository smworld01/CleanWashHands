package com.hands.clean.function.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hands.clean.R
import com.hands.clean.function.receiver.ACTION_REGISTER_NOTIFICATION_DEVICE

class NewDeviceNotify(private val context: Context) : Notify(context) {
    override val channelId: String = "Bluetooth"
    override val contentTitle: String = "새로운 기기에 연결되었습니다."
    override val channelName: String = context.getString(R.string.channel_bluetooth_name)

    fun addRegisterDeviceAction(address: String): Notify {
        val intent: Intent = createDeviceIntent(address)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder.addAction(R.drawable.ic_baseline_add_24, "등록", pendingIntent)
        return this
    }

    private fun createDeviceIntent(address: String): Intent {
        val intent: Intent = Intent(ACTION_REGISTER_NOTIFICATION_DEVICE)
        intent.putExtra("address", address)
        return intent
    }
}