package com.hands.clean.function.permission

class PermissionCallback {
    private var granted: () -> Unit = {}
    private var denied: () -> Unit = {}
    private var requestFailed: () -> Unit = {}

    fun registerGranted(callback :() -> Unit) {
        granted = callback
    }
    fun registerDenied(callback :() -> Unit) {
        denied = callback
    }
    fun registerRequestFailed(callback :() -> Unit) {
        requestFailed = callback
    }

    fun callbackGranted() {
        return granted()
    }
    fun callbackDenied() {
        return denied()
    }
    fun callbackRequestFailed() {
        return requestFailed()
    }
}