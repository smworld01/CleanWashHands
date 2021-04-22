package com.hands.clean.function.notification

object NotificationIdCounter {
    private var notificationId: Int = 0

    // sync
    fun getNotificationId(): Int  {
        return notificationId++
    }
}