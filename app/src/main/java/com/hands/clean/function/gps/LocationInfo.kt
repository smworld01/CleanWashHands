package com.hands.clean.function.gps

object LocationInfo {
    var latitude: Double? = null
    var longitude: Double? = null

    fun onRemove() {
        latitude = null
        longitude = null
    }

    fun isEnable(): Boolean {
        return latitude != null && longitude != null
    }

    fun isDisable(): Boolean {
        return !isEnable()
    }
}