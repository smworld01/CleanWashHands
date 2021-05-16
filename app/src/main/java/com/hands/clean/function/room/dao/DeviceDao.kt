package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import com.hands.clean.function.room.entry.DeviceEntry


interface DeviceDao {
    fun getAll(): List<DeviceEntry>

    fun loadAllByIds(userIds: IntArray): List<DeviceEntry>

    fun findByAddress(address: String): DeviceEntry?

    fun findByName(name: String): DeviceEntry?

    fun changeNotificationByAddress(address: String, bool: Boolean)

    fun changeNameByAddress(address: String, name: String)

    fun insertAll(vararg users: DeviceEntry)

    fun delete(user: DeviceEntry)
}