package com.hands.clean.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hands.clean.function.room.DB
import kotlin.concurrent.thread

class WashReceiver : BroadcastReceiver() {
    override fun onReceive(context:  Context, intent: Intent) {
        recordWash(context, intent)
    }

    private fun recordWash(context: Context, intent: Intent) {
        thread {
            val recordId = intent.getIntExtra("recordId",1)
            val notificationId = intent.getIntExtra("notificationId", 5)

            val washEntry = DB.getInstance().washDao().findById(recordId)

            washEntry.wash = true

            DB.getInstance().washDao().update(washEntry)
        }
    }
}