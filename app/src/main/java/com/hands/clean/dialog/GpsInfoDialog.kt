package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry

class GpsInfoDialog(private val gpsEntry: GpsEntry): BottomSheetDialogFragment() {
    private var cancelCallback: () -> Unit = {}
    private var dismissCallback: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_gps_info, container, false)
    }
    private lateinit var notifySwitch: SwitchCompat

    private lateinit var editTextName: EditText
    private lateinit var editTextLatitude: EditText
    private lateinit var editTextLongitude: EditText
    private lateinit var editTextRadius: EditText


    private lateinit var textViewRegisteredDate: TextView
    private lateinit var textViewLastNotifyDate: TextView

    private lateinit var deleteButton: Button

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val copiedEntry = gpsEntry.copy()

        copiedEntry.isNotification = notifySwitch.isChecked

        if (editTextName.text.isNotBlank()) {
            copiedEntry.name = editTextName.text.toString()
        }
        try {
            copiedEntry.radius = editTextRadius.text.toString().toFloat()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (copiedEntry != gpsEntry) {
            DB.getInstance().gpsDao().updateAll(copiedEntry)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        assignText()

        notifySwitch.isChecked = gpsEntry.isNotification

        deleteButton.setOnClickListener{
            val deleteDialog = TrackerDeleteDialog(gpsEntry)
            deleteDialog.show(parentFragmentManager, deleteDialog.tag)
            dismiss()
        }
    }

    private fun initView(view: View) {
        notifySwitch = view.findViewById(R.id.notifySwitch)

        editTextName = view.findViewById(R.id.name)
        editTextLatitude = view.findViewById(R.id.latitude)
        editTextLongitude = view.findViewById(R.id.longitude)
        editTextRadius = view.findViewById(R.id.radius)

        textViewRegisteredDate = view.findViewById(R.id.registeredDate)
        textViewLastNotifyDate = view.findViewById(R.id.lastNotifyDate)
        deleteButton = view.findViewById(R.id.deleteButton)
    }

    private fun assignText() {
        editTextName.setText(gpsEntry.name)
        editTextLatitude.setText(gpsEntry.latitude.toString())
        editTextLongitude.setText(gpsEntry.longitude.toString())
        editTextRadius.setText(gpsEntry.radius.toString())

        textViewRegisteredDate.text = gpsEntry.registrationTime

        val lastNotifyTime = gpsEntry.lastNotifyTime
        if (lastNotifyTime == null) {
            textViewLastNotifyDate.text = "없음"
        } else {
            textViewLastNotifyDate.text = lastNotifyTime
        }
    }
}