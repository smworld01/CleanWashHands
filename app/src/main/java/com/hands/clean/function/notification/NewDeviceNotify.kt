package com.hands.clean.function.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.receiver.ACTION_REGISTER_NOTIFICATION_DEVICE

class NewDeviceNotify(private val context: Context, private val notifyInfo: NotifyInfo) : Notify(context, notifyInfo) {
    override val contentTitle: String = "새로운 기기에 연결되었습니다."
    override lateinit var contentText: String


    fun addRegisterDeviceAction(address: String): Notify {
        val intent: Intent = createDeviceIntent(address)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder.addAction(R.drawable.ic_baseline_add_24, "등록", pendingIntent)
        return this
    }

    private fun createDeviceIntent(address: String): Intent {
        val intent: Intent = Intent(ACTION_REGISTER_NOTIFICATION_DEVICE)
        intent.putExtra("address", address)
        intent.putExtra("type", channelId)
        return intent
    }
    override fun send() {
        sendNotification()
    }

    fun send(device: String) {
        this.contentText = "새로운 ${notifyInfo.channelId} 기기 ${device}를 등록하시겠습니까?"
        super.setNotification()
        addRegisterDeviceAction(device)

        send()
    }
}