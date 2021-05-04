package com.hands.clean.function.notification.factory

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.function.notification.type.NotifyInfo

abstract class NotificationFactory {
    abstract val context: Context

    abstract val notifyInfo: NotifyInfo


    fun build(): Notification {
        val builder: NotificationCompat.Builder = initBuilder()
        setIcon(builder)
        setContentText(builder)
        setOther(builder)
        setButton(builder)
        return builder.build()
    }


    private fun initBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, notifyInfo.channelId)
    }

    abstract fun setIcon(builder: NotificationCompat.Builder)
    abstract fun setContentText(builder: NotificationCompat.Builder)
    abstract fun setOther(builder: NotificationCompat.Builder)
    abstract fun setButton(builder: NotificationCompat.Builder)

}