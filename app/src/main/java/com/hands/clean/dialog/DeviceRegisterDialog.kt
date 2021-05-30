package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.room.entry.WifiEntry

class DeviceRegisterDialog(private val deviceEntry: DeviceEntry): BottomSheetDialogFragment() {
    private var cancelCallback: () -> Unit = {}
    private var dismissCallback: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_device_register, container, false)
    }
    private lateinit var editTextName: EditText
    private lateinit var button: Button

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextName = view.findViewById(R.id.name)

        button = view.findViewById(R.id.button)

        button.setOnClickListener{
            checkButtonMode()
        }
    }

    private fun checkButtonMode() {
        when {
            editTextName.text.isEmpty() -> nameDescriptionMode()
            DB.getInstance().matchDaoByEntry(deviceEntry)
                .findByAddress(deviceEntry.address) != null -> modifyMode()
            else -> createMode()
        }
    }

    private fun createMode() {
        deviceEntry.name = editTextName.text.toString()
        when(deviceEntry) {
            is WifiEntry -> DB.getInstance().wifiDao().insertAll(deviceEntry)
            is BluetoothEntry -> DB.getInstance().bluetoothDao().insertAll(deviceEntry)
            else -> throw Exception()
        }
        Toast.makeText(this.context, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    private fun modifyMode() {
        val entry = DB.getInstance().matchDaoByEntry(deviceEntry).findByAddress(deviceEntry.address)
        entry!!.name = editTextName.text.toString()
        entry!!.isNotification = true

        when(entry) {
            is WifiEntry -> DB.getInstance().wifiDao().updateAll(entry)
            is BluetoothEntry -> DB.getInstance().bluetoothDao().updateAll(entry)
            else -> throw Exception()
        }
        Toast.makeText(this.context, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    private fun nameDescriptionMode() {
        Toast.makeText(this.context, "이름을 써주세요.", Toast.LENGTH_SHORT).show()
    }
}