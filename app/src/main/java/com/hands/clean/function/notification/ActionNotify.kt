package com.hands.clean.function.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.Drawable
import com.hands.clean.R

abstract class ActionNotify(private val context: Context) : Notify(context) {

    fun addAction(drawable: Int, title: String, pendingIntent: PendingIntent): ActionNotify {
        builder.addAction(drawable, title, pendingIntent)
        return this
    }
}