package com.hands.clean.function.room.entry

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class LocationEntry(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val date: String,
    val latitude: Double,
    val longitude: Double,
)

class LocationEntryBuilder() {
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    fun setLocation(position: LatLng) {
        latitude = position.latitude
        longitude = position.longitude
    }

    fun setLocation(position: Location) {
        latitude = position.latitude
        longitude = position.longitude
    }

    fun build(): LocationEntry {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val date: String = mFormat.format(Date())
        return LocationEntry(
            0, date, latitude, longitude
        )
    }
}