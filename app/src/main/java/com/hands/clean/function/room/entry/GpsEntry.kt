package com.hands.clean.function.room.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.Marker
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class GpsEntry(
    @PrimaryKey(autoGenerate = true) override val uid: Int,
    @ColumnInfo(name = "name") override var name: String,
    @ColumnInfo(name = "requestId") val requestId: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "radius") val radius: Float,
    @ColumnInfo(name = "notification") override var isNotification: Boolean = false,
    @ColumnInfo(name = "last_notify_time") override var lastNotifyTime: String? = null,
    @ColumnInfo(name = "registration_time") override val registrationTime: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK).format(Date()),
) : TrackerEntry {
    @Ignore override val notifyInfo: NotifyInfo = NotifyType.GPS
    @Ignore var marker: Marker? = null
    @Ignore var circle: Circle? = null
}