package com.hands.clean.view

import com.hands.clean.R
import android.content.Context

import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class CustomTextViewDescription : ConstraintLayout {
    var layout: ConstraintLayout? = null
    var textViewTitle: TextView? = null
    var textViewContext: TextView? = null


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
        inflate(context, R.layout.custom_text_view_description, this)
        layout = findViewById(R.id.layout)
        textViewTitle= findViewById(R.id.textViewTitle)
        textViewContext= findViewById(R.id.textViewContext)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewDescription)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewDescription, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val title =
            typedArray.getString(R.styleable.CustomTextViewDescription_contentTitle)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.CustomTextViewDescription_contentContext)
        setTextViewContext(context)
        typedArray.recycle()
    }

    fun setTextViewTitle(text: String?) {
        textViewTitle?.text=text
    }

    fun setTextViewContext(text: String?) {
        textViewContext?.text=text
    }

}