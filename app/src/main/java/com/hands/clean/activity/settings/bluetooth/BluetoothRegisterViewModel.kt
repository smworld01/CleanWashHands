package com.hands.clean.activity.settings.bluetooth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hands.clean.function.room.entry.BluetoothEntry

class BluetoothRegisterViewModel: ViewModel() {
    val createBluetoothEntry = MutableLiveData<BluetoothEntry?>()
}