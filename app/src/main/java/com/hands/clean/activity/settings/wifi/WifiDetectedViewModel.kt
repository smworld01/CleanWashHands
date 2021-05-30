package com.hands.clean.activity.settings.wifi

import android.net.wifi.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hands.clean.function.room.entry.WifiEntry

class WifiDetectedViewModel: ViewModel() {
    val createWifiEntry = MutableLiveData<WifiEntry?>()
}