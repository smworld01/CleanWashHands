package com.hands.clean.function.notification.notify

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.function.notification.NotificationIdCounter

interface Notify {
    fun onNotify()
}