package com.hands.clean.function.room.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType

@Entity
data class BluetoothEntry(
        @PrimaryKey(autoGenerate = true) override val uid: Int,
        @ColumnInfo(name = "name") override val name: String,
        @ColumnInfo(name = "address") override val address: String,
        @ColumnInfo(name = "notification") override val isNotification: Boolean,
): DeviceEntry {
        override val notifyInfo: NotifyInfo = NotifyType.Bluetooth
}
