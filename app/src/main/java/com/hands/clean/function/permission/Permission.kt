package com.hands.clean.function.permission

interface Permission {
    fun request()

    fun isGranted(): Boolean
    fun isDenied(): Boolean
}