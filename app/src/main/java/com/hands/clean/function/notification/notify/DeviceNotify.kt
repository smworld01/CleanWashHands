package com.hands.clean.function.notification.notify

import android.content.Context
import com.hands.clean.function.notification.factory.notify.NewDeviceDetectionNotifyFactory
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.DeviceEntry

abstract class DeviceNotify: Notify {
    protected abstract val deviceEntry: DeviceEntry
    protected abstract val context: Context

    override fun onNotify() {
        if (isNotify()) {
            doNotify()
        }
    }

    protected abstract fun isNotify(): Boolean

    private fun doNotify() {
        val foundDeviceInfo: DeviceEntry? = findDeviceInDB()

        if (isNewDevice(foundDeviceInfo)) {
            sendNewDeviceNotify()
        } else {
            sendNotify(foundDeviceInfo!!)
        }
    }

    private fun findDeviceInDB(): DeviceEntry? {
        return DB.getInstance().matchDaoByEntry(deviceEntry).findByAddress(deviceEntry.address)
    }

    private fun isNewDevice(device : DeviceEntry?): Boolean {
        return device == null
    }

    private fun sendNewDeviceNotify() {
        if (isNewDeviceNotify()) {
            registerDeviceInDB()

            askUserForNewDeviceIsNotification()
        }
    }
    protected abstract fun isNewDeviceNotify(): Boolean

    private fun registerDeviceInDB() {
        DB.getInstance().matchDaoByEntry(deviceEntry).insertAll(deviceEntry)
    }

    private fun askUserForNewDeviceIsNotification() {
        NewDeviceDetectionNotifyFactory(context, deviceEntry).onBuild()
            .onNotify()
    }

    protected abstract fun sendNotify(foundDevice: DeviceEntry)
}