package com.hands.clean.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.hands.clean.R

class ProgressDialog(context: Context): Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
    }

}