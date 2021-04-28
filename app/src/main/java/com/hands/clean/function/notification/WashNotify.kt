package com.hands.clean.function.notification

import android.content.Context
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class WashNotify(context: Context, notifyInfo: NotifyInfo) : Notify(context, notifyInfo) {
    override val contentTitle: String = "손을 씻어주세요."
    override lateinit var contentText: String

    private fun recordNotification() {
        val now = Date()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val we = WashEntry(0, format.format(now), channelId, "", false)

        thread {
            DB.getInstance().washDao().insertAll(we)

        }
    }
    override fun send() {
        recordNotification()
        sendNotification()
    }

    fun send(contentText: String) {
        this.contentText = contentText
        super.setNotification()

        send()
    }
}