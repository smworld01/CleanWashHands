package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.room.entry.WifiEntry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DeviceInfoDialog(private val deviceEntry: DeviceEntry): BottomSheetDialogFragment() {
    private var cancelCallback: () -> Unit = {}
    private var dismissCallback: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_device_info, container, false)
    }
    lateinit var notifySwitch: SwitchCompat

    lateinit var textViewDeviceName: TextView
    lateinit var editTextName: EditText
    lateinit var textViewRegisteredDate: TextView
    lateinit var textViewLastNotifyDate: TextView

    lateinit var deleteButton: Button

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val copiedEntry = when(deviceEntry) {
            is WifiEntry -> {deviceEntry.copy()}
            is BluetoothEntry -> {deviceEntry.copy()}
            else -> throw Exception()
        }

        copiedEntry.isNotification = notifySwitch.isChecked

        if (editTextName.text.isNotBlank()) {
            copiedEntry.name = editTextName.text.toString()
        }

        if (copiedEntry != deviceEntry) {
            when (deviceEntry) {
                is WifiEntry -> DB.getInstance().wifiDao().updateAll(copiedEntry)
                is BluetoothEntry -> DB.getInstance().bluetoothDao().updateAll(copiedEntry)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        assignText()

        notifySwitch.isChecked = deviceEntry.isNotification

        deleteButton.setOnClickListener{
            val deleteDialog = TrackerDeleteDialog(deviceEntry)
            deleteDialog.show(parentFragmentManager, deleteDialog.tag)
            dismiss()
        }
    }

    private fun initView(view: View) {
        notifySwitch = view.findViewById(R.id.notifySwitch)
        textViewDeviceName = view.findViewById(R.id.deviceName)
        editTextName = view.findViewById(R.id.name)
        textViewRegisteredDate = view.findViewById(R.id.registeredDate)
        textViewLastNotifyDate = view.findViewById(R.id.lastNotifyDate)
        deleteButton = view.findViewById(R.id.deleteButton)
    }

    private fun assignText() {
        textViewDeviceName.text = deviceEntry.deviceName
        editTextName.setText(deviceEntry.name)
        textViewRegisteredDate.text = deviceEntry.registrationTime

        val lastNotifyTime = deviceEntry.lastNotifyTime
        if (lastNotifyTime == null) {
            textViewLastNotifyDate.text = "없음"
        } else {
            textViewLastNotifyDate.text = lastNotifyTime
        }
    }
}