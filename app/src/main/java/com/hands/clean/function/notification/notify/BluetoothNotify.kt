package com.hands.clean.function.notification.notify

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.settings.WashSettingsManager

class BluetoothNotify(override val context: Context, intent: Intent) : DeviceNotify() {
    private val settings = WashSettingsManager(context)
    private val deviceInfo = getDeviceInIntent(intent)

    override val deviceEntry = BluetoothEntry(0, deviceInfo.name, deviceInfo.address, false)

    private fun getDeviceInIntent(intent: Intent): BluetoothDevice {
        return intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
    }

    override fun isNotify(): Boolean {
        return settings.bluetoothNotify
    }


    override fun isNewDeviceNotify(): Boolean {
        return settings.bluetoothNewDeviceNotify
    }

    override fun sendNotify(foundDevice: DeviceEntry) {
        if (foundDevice.isNotification) {
            WashNotifyFactory(context, deviceEntry).onBuild()
                .onNotify()
        }
    }
}