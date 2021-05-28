package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.NotifyLimiter

abstract class LimiterNotifyFactory(context: Context): NotifyFactory {
    private val notifyLimiter = NotifyLimiter(context)
    fun onBuildWithLimiter(): Notify? {
        if (notifyLimiter.isNotLimited()) {
            return onBuild()
        }
        return null
    }
}