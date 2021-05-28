package com.hands.clean.activity.settings.gps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.hands.clean.function.room.entry.GpsEntry

class MapsViewModel: ViewModel() {
    val selectPosition = MutableLiveData<LatLng>().apply {
        value = LatLng(0.0,0.0)
    }
    val selectRadius = MutableLiveData<Double>()

    val clickInRecycler = MutableLiveData<GpsEntry?>()
    val longClickInRecycler = MutableLiveData<GpsEntry?>()
}