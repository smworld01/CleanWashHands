package com.hands.clean.function.notification.factory.notify

import com.hands.clean.function.notification.notify.Notify

interface NotifyFactory {
    fun onBuild(): Notify
}