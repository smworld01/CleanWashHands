package com.hands.clean.function.notification.factory

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.type.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.room.entrys.DeviceEntry

abstract class NotificationFactory(locationInfo: DeviceEntry) {
    val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)
    val channelId: String = notifyInfo.channelId

    abstract val context: Context

    fun create(): Notify {
        val builder: NotificationCompat.Builder = initBuilder()
        setIcon(builder)
        setContentText(builder)
        setOther(builder)
        setButton(builder)

        return setNotify(builder.build())
    }


    private fun initBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
    }

    abstract fun setIcon(builder: NotificationCompat.Builder)
    abstract fun setContentText(builder: NotificationCompat.Builder)
    abstract fun setOther(builder: NotificationCompat.Builder)
    abstract fun setButton(builder: NotificationCompat.Builder)


    abstract fun setNotify(notification: Notification) : Notify
}