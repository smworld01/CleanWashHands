package com.hands.clean.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hands.clean.R
import com.hands.clean.activity.settings.MapsViewModel

class GpsRegisterButtonDialog(private val mapsViewModel: MapsViewModel): BottomSheetDialogFragment() {
    private var cancelCallback: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_gps_register_button, container, false)
    }

    fun setOnCancelListener(callback: () -> Unit) {
        cancelCallback = callback
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button)

        button.setOnClickListener {
            val gpsRegisterDialog = GpsRegisterDialog(mapsViewModel)
            gpsRegisterDialog.setOnDismissListener(cancelCallback)
            gpsRegisterDialog.show(parentFragmentManager, gpsRegisterDialog.tag)
            dismiss()
        }
    }
}