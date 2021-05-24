package com.hands.clean.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.TrackerEntry
import com.hands.clean.function.room.entry.WifiEntry

class TrackerDeleteDialog(private val trackerEntry: TrackerEntry): BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_tracker_delete, container, false)
    }

    private lateinit var deleteButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)

        deleteButton.setOnClickListener{
            when (trackerEntry) {
                is WifiEntry -> DB.getInstance().wifiDao().delete(trackerEntry)
                is BluetoothEntry -> DB.getInstance().bluetoothDao().delete(trackerEntry)
                is GpsEntry -> DB.getInstance().gpsDao().delete(trackerEntry)
            }
            dismiss()
        }
    }

    private fun initView(view: View) {
        deleteButton = view.findViewById(R.id.button)
    }
}