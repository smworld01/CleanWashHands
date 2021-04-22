package com.hands.clean.function.notification

import android.content.Context
import android.util.Log
import com.hands.clean.function.room.WashEntry
import com.hands.clean.function.room.db
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

abstract class RecordNotify(context: Context): Notify(context) {
    private fun recordNotification() {
        val now = Date()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val we = WashEntry(0, format.format(now), channelId, "", false)

        thread {
            db.washDao().insertAll(we)

        }
    }
    override fun send() {
        recordNotification()
        sendNotification()
    }
}