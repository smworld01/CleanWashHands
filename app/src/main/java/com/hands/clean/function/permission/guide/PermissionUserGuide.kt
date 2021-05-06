package com.hands.clean.function.permission.guide

interface PermissionUserGuide {
    fun showRequest(callback: () -> Unit)
    fun showRequestFail(callback: () -> Unit)
    fun showExcuse(callback: () -> Unit)
}