package com.hands.clean.function.notification.factory.notification

import android.app.Notification

interface NotificationFactory {
    fun onBuild() : Notification
}