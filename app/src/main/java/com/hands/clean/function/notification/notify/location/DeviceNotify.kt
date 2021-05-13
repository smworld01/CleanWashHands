package com.hands.clean.function.notification.notify.location

import android.content.Context
import com.hands.clean.function.notification.factory.notify.NewDeviceDetectionNotifyFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.DeviceEntry

abstract class DeviceNotify: LocationNotify() {
    protected abstract val deviceEntry: DeviceEntry
    protected abstract val context: Context

    abstract override fun isNotify(): Boolean

    override fun doNotify() {
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

    abstract fun sendNotify(foundDevice: DeviceEntry)
}