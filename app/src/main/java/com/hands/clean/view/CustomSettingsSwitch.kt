package com.hands.clean.view

import com.hands.clean.R
import android.content.Context

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class CustomSettingsSwitch : ConstraintLayout {
    var layoutSwitch: ConstraintLayout? = null
    var textViewTitleSwitch: TextView? = null
    var textViewContextSwitch: TextView? = null
    var switch: Switch? = null



    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    private fun initView() {
        inflate(getContext(), R.layout.button_settings_nonimage, this);
        layoutSwitch = findViewById(R.id.layoutSwitch)
        textViewTitleSwitch = findViewById(R.id.textViewTitleSwitch)
        textViewContextSwitch = findViewById(R.id.textViewContextSwitch)
        switch = findViewById<Switch>(R.id.switch1)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SwitchSettings)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SwitchSettings, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val title =
            typedArray.getString(R.styleable.SwitchSettings_contentTitleSwitch)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.SwitchSettings_contentContextSwitch)
        setTextViewContext(context)
        typedArray.recycle()

    }

    fun setTextViewTitle(text: String?) {
        textViewTitleSwitch?.text=text
    }

    fun setTextViewContext(text: String?) {
        textViewContextSwitch?.text=text
    }

}