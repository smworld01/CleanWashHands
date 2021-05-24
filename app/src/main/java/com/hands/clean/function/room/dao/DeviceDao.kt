package com.hands.clean.function.room.dao

import com.hands.clean.function.room.entry.DeviceEntry


interface DeviceDao {
    fun getAll(): List<DeviceEntry>

    fun loadAllByIds(userIds: IntArray): List<DeviceEntry>

    fun findByAddress(address: String): DeviceEntry?

    fun findByName(name: String): DeviceEntry?

    fun insertAll(vararg entries: DeviceEntry)

    fun updateAll(vararg entries: DeviceEntry)

    fun delete(entry: DeviceEntry)
}