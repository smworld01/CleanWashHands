package com.hands.clean.function.notification.notify.location

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.settings.WashSettingsManager

class BluetoothNotify(context: Context, intent: Intent) : Notify {
    private val notify = NotifyProcess(context, intent)

    override fun onNotify() {
        notify.onNotify()
    }

    class NotifyProcess(override val context: Context, intent: Intent) : DeviceNotify() {
        private val settings = WashSettingsManager(context)
        private val deviceInfo = getDeviceInIntent(intent)

        override val deviceEntry = BluetoothEntry(0, deviceInfo.name, deviceInfo.address, false)

        private fun getDeviceInIntent(intent: Intent): BluetoothDevice {
            return intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
        }


        override fun notifySettingsIsEnable(): Boolean {
            return settings.bluetoothNotify
        }

        override fun newDeviceNotifySettingsIsEnable(): Boolean {
            return settings.bluetoothNewDeviceNotify
        }

        override fun sendNotify(foundDevice: DeviceEntry) {
            if (foundDevice.isNotification) {
                WashNotifyFactory(context, foundDevice).onBuild()
                    .onNotify()
            }
        }
    }
}

