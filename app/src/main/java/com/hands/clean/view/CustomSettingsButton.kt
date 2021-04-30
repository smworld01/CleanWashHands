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


class CustomSettingsButton : ConstraintLayout {
    var layout: ConstraintLayout? = null
    var imageView: ImageView? = null
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
        inflate(getContext(), R.layout.button_settings, this);
        layout = findViewById(R.id.layout)
        imageView= findViewById(R.id.imageView)
        textViewTitle= findViewById(R.id.textViewTitle)
        textViewContext= findViewById(R.id.textViewContext)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SettingsButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SettingsButton, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val image =
            typedArray.getDrawable(R.styleable.SettingsButton_contentImage)
        setImageView(image)
        val title =
            typedArray.getString(R.styleable.SettingsButton_contentTitle)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.SettingsButton_contentContext)
        setTextViewContext(context)
        typedArray.recycle()
    }

    fun setImageView(drawable: Drawable?) {
        imageView?.setImageDrawable(drawable)
    }

    fun setTextViewTitle(text: String?) {
        textViewTitle?.text=text
    }

    fun setTextViewContext(text: String?) {
        textViewContext?.text=text
    }

}