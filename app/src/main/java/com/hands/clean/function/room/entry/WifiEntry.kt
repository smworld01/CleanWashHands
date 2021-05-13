package com.hands.clean.function.room.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WifiEntry(
        @PrimaryKey(autoGenerate = true) override val uid: Int,
        @ColumnInfo(name = "name") override val name: String,
        @ColumnInfo(name = "address") override val address: String,
        @ColumnInfo(name = "notification") override val isNotification: Boolean,
): DeviceEntry
