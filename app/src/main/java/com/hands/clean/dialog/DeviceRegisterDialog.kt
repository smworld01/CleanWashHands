package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.function.room.entry.WifiEntry

class DeviceRegisterDialog(private val ViewModel: ViewModel): BottomSheetDialogFragment() {
    private var cancelCallback: () -> Unit = {}
    private var dismissCallback: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_gps_register, container, false)
    }
        lateinit var editTextName: EditText
        lateinit var button: Button

    fun setOnCancelListener(callback: () -> Unit) {
        cancelCallback = callback
    }

    fun setOnDismissListener(callback: () -> Unit) {
        dismissCallback = callback
    }

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
            else -> createMode()
        }
    }

    private fun createMode() {
        WifiEntry(
            0,
            "name",
            "deviceName",
            "address",
            true
        )
    }

    private fun nameDescriptionMode() {
        Toast.makeText(this.context, "이름을 써주세요.", Toast.LENGTH_SHORT).show()
    }
}