package com.hands.clean.function.settings

import android.content.Context

class WashSettingsManager(val context: Context) {
    var bluetoothNotify: Boolean
        get() = getPreferences(::bluetoothNotify.name)
        set(value) = setPreferences(::bluetoothNotify.name, value)
    var wifiNotify: Boolean
        get() = getPreferences(::wifiNotify.name)
        set(value) = setPreferences(::wifiNotify.name, value)
    var gpsNotify: Boolean
        get() = getPreferences(::gpsNotify.name)
        set(value) = setPreferences(::gpsNotify.name, value)

    var bluetoothNewDeviceNotify: Boolean
        get() = getPreferences(::bluetoothNewDeviceNotify.name)
        set(value) = setPreferences(::bluetoothNewDeviceNotify.name, value)
    var wifiNewDeviceNotify: Boolean
        get() = getPreferences(::wifiNewDeviceNotify.name)
        set(value) = setPreferences(::wifiNewDeviceNotify.name, value)
    var wifiEncryptionNotify: Boolean
        get() = getPreferences(::wifiEncryptionNotify.name)
        set(value) = setPreferences(::wifiEncryptionNotify.name, value)

    var tutorial: Boolean
        get() = getPreferences(::tutorial.name)
        set(value) = setPreferences(::tutorial.name, value)
    var tutorialMaps: Boolean
        get() = getPreferences(::tutorialMaps.name)
        set(value) = setPreferences(::tutorialMaps.name, value)


    private fun setPreferences(key: String, value: Boolean) {
        val sharedPref = context.getSharedPreferences("com.hands.clean.SETTINGS", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    private fun getPreferences(key: String): Boolean {
        val sharedPref = context.getSharedPreferences("com.hands.clean.SETTINGS", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, false)
    }
}