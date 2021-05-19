package com.hands.clean.activity.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hands.clean.function.room.entry.TrackerEntry

class TrackerListViewModel: ViewModel() {
    val searchTrackerEntry = MutableLiveData<TrackerEntry?>()
}