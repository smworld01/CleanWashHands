package com.hands.clean.function.notification


// notificationId
// 1 == service
//
object NotificationIdCounter {
    private var notificationId: Int = 5

    @Synchronized fun getNotificationId(): Int  {
        return notificationId++
    }
}