package com.hands.clean.function.notification.notify.location

import com.hands.clean.function.notification.notify.Notify

abstract class LocationNotify: Notify {
    override fun onNotify() {
        doNotify()
    }
    abstract fun notifySettingsIsEnable(): Boolean
    abstract fun doNotify()
}