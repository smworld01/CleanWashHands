package com.hands.clean.view

import com.hands.clean.R
import android.content.Context

import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout


class CustomSwitch : ConstraintLayout {
    lateinit var layout: ConstraintLayout
    lateinit var textViewTitle: TextView
    lateinit var textViewContext: TextView
    lateinit var switch: SwitchCompat



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
        inflate(context, R.layout.custom_switch, this);
        layout = findViewById(R.id.layout)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewContext = findViewById(R.id.textViewContext)
        switch = findViewById(R.id.customSwitch)

        layout.setOnClickListener {
            switch.isChecked = !switch.isChecked
        }
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomSwitch)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomSwitch, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val title =
            typedArray.getString(R.styleable.CustomSwitch_contentTitle)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.CustomSwitch_contentContext)
        setTextViewContext(context)
        typedArray.recycle()

    }

    private fun setTextViewTitle(text: String?) {
        textViewTitle.text=text
    }

    private fun setTextViewContext(text: String?) {
        textViewContext.text=text
    }

}