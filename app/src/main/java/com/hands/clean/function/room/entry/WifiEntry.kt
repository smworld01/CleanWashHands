package com.hands.clean.function.room.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class WifiEntry(
    @PrimaryKey(autoGenerate = true) override val uid: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "address") override val address: String,
    @ColumnInfo(name = "notification") override val isNotification: Boolean,
    @ColumnInfo(name = "last_notify_time") override var lastNotifyTime: String? = null,
    @ColumnInfo(name = "registration_time") override val registrationTime: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK).format(
        Date()
    ),
): DeviceEntry {
    @Ignore override val notifyInfo: NotifyInfo = NotifyType.Wifi
}
