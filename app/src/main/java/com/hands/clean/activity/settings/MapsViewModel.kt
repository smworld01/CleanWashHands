package com.hands.clean.activity.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.hands.clean.function.room.entry.GpsEntry

class MapsViewModel: ViewModel() {
    val selectPosition = MutableLiveData<LatLng>().apply {
        value = LatLng(0.0,0.0)
    }
    val selectRadius = MutableLiveData<Double>()

    val createGpsEntry = MutableLiveData<GpsEntry?>()
}