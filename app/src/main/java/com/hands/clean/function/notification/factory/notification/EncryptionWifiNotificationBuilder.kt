package com.hands.clean.function.notification.factory.notification

import android.content.Context
import com.hands.clean.function.room.entry.*

class EncryptionWifiNotificationBuilder(
    context: Context,
    trackerEntry: TrackerEntry,
    notificationId: Int,
) : WashNotificationBuilder(context, trackerEntry, notificationId) {

    override fun getContentText(): String {
        return "암호가 있는 와이파이에 연결되었습니다."
    }
}