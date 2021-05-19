package com.hands.clean.activity.settings.wifi

import android.net.wifi.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hands.clean.function.room.entry.WifiEntry

class WifiScanViewModel: ViewModel() {
    val wifiScanResult = MutableLiveData<List<ScanResult>>()
    val createWifiEntry = MutableLiveData<WifiEntry?>()
}