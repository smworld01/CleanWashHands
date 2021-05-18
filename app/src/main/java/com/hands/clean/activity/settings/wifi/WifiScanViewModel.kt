package com.hands.clean.activity.settings.wifi

import android.net.wifi.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WifiScanViewModel: ViewModel() {
    val wifiScanResult = MutableLiveData<List<ScanResult>>()
}