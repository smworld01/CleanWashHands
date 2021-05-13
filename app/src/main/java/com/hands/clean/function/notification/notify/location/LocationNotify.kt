package com.hands.clean.function.notification.notify.location

import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entrys.LocationEntry

abstract class LocationNotify: Notify {
    override fun onNotify() {
        if (isNotify()) {
            doNotify()
        }
    }
    abstract fun isNotify(): Boolean
    abstract fun doNotify()
}