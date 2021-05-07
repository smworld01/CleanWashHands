package com.hands.clean.function.gps

interface LocationGetter {
    fun getLatitude(): Double

    fun getLongitude(): Double
}