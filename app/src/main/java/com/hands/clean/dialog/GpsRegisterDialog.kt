package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.activity.settings.MapsViewModel
import com.hands.clean.function.room.entry.GpsEntry

class GpsRegisterDialog(private val mapsViewModel: MapsViewModel): BottomSheetDialogFragment() {
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
        lateinit var editTextRadius: EditText
        lateinit var button: Button

    fun setOnCancelListener(callback: () -> Unit) {
        cancelCallback = callback
    }

    fun setOnDismissListener(callback: () -> Unit) {
        dismissCallback = callback
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mapsViewModel.selectRadius.value = 0.0
        dismissCallback()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelCallback()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextName = view.findViewById(R.id.name)
        editTextRadius = view.findViewById(R.id.radius)
        button = view.findViewById(R.id.button)

        editTextRadius.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                mapsViewModel.selectRadius.value = it.toString().toDouble()
            } else {
                mapsViewModel.selectRadius.value = 0.0
            }
        }
        editTextName.addTextChangedListener {
        }

        button.setOnClickListener{
            checkButtonMode()
        }
    }

    private fun checkButtonMode() {
        when {
            editTextName.text.isEmpty() -> nameDescriptionMode()
            editTextRadius.text.isEmpty() -> radiusDescriptionMode()
            editTextRadius.text.toString().toDouble() !in 20.0..100.0 -> radiusRangeDescriptionMode()
            else -> createMode()
        }
    }

    private fun createMode() {
        mapsViewModel.createGpsEntry.value =
            GpsEntry(0,
                editTextName.text.toString(),
                editTextName.text.hashCode().toString(),
                mapsViewModel.selectPosition.value!!.latitude,
                mapsViewModel.selectPosition.value!!.longitude,
                editTextRadius.text.toString().toFloat(),
                true
            )
        dismiss()
    }

    private fun nameDescriptionMode() {
        Toast.makeText(this.context, "이름을 써주세요.", Toast.LENGTH_SHORT).show()
    }

    private fun radiusDescriptionMode() {
        Toast.makeText(this.context, "범위가 입력해주세요!", Toast.LENGTH_SHORT).show()
    }

    private fun radiusRangeDescriptionMode() {
        Toast.makeText(this.context, "범위는 20~100이에요.", Toast.LENGTH_SHORT).show()
    }
}