package com.hands.clean.function.service

import android.content.Context

open class WashLocationServiceManager(
    private val context: Context,
): MyServiceManager<WashLocationService>(context, WashLocationService::class.java)