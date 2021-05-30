package com.hands.clean.function.notification.notify.location

import android.content.Context
import com.hands.clean.function.notification.factory.notify.NewDeviceDetectionNotifyFactory
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.DeviceEntry

abstract class DeviceNotify: LocationNotify() {
    abstract val deviceEntry: DeviceEntry
    abstract val context: Context

    override fun doNotify() {
        val deviceInfo: DeviceEntry? = findDeviceInDB()

        if (deviceInfoIsNotExist(deviceInfo)) {
            registerDeviceInDB()

            sendNewDeviceNotify()
        } else if (deviceInfoIsRegistered(deviceInfo!!)) {
            sendNotify(deviceInfo)
        }
    }

    private fun findDeviceInDB(): DeviceEntry? {
        return DB.getInstance().matchDaoByEntry(deviceEntry).findByAddress(deviceEntry.address)
    }

    private fun deviceInfoIsNotExist(device : DeviceEntry?): Boolean {
        return device == null
    }

    private fun sendNewDeviceNotify() {
        if (newDeviceNotifySettingsIsEnable()) {
            askUserForNewDeviceIsNotification()
        }
    }
    private fun deviceInfoIsRegistered(device: DeviceEntry): Boolean {
        return device.name != ""
    }

    abstract fun newDeviceNotifySettingsIsEnable(): Boolean

    private fun registerDeviceInDB() {
        DB.getInstance().matchDaoByEntry(deviceEntry).insertAll(deviceEntry)
    }

    private fun askUserForNewDeviceIsNotification() {
        NewDeviceDetectionNotifyFactory(context, deviceEntry).onBuild()
            .onNotify()
    }

    abstract fun sendNotify(foundDevice: DeviceEntry)
}