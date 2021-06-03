package com.hands.clean.view

import com.hands.clean.R
import android.content.Context

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class CustomSettingsButtonNonImage : ConstraintLayout {
    var layoutNonImage: ConstraintLayout? = null
    var textViewTitleNonImage: TextView? = null
    var textViewContextNonImage: TextView? = null


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
        layoutNonImage = findViewById(R.id.layoutNonImage)
        textViewTitleNonImage= findViewById(R.id.textViewTitleNonImage)
        textViewContextNonImage= findViewById(R.id.textViewContextNonImage)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SettingsButtonNonImage)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SettingsButtonNonImage, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val title =
            typedArray.getString(R.styleable.SettingsButtonNonImage_contentTitleNonImage)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.SettingsButtonNonImage_contentContextNonImage)
        setTextViewContext(context)
        typedArray.recycle()
    }

    fun setTextViewTitle(text: String?) {
        textViewTitleNonImage?.text=text
    }

    fun setTextViewContext(text: String?) {
        textViewContextNonImage?.text=text
    }

}