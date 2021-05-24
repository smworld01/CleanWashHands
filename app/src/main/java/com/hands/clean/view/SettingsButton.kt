package com.hands.clean.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hands.clean.R


class SettingsButton : ConstraintLayout {
    private var layout: ConstraintLayout? = null
    private var imageView: ImageView? = null
    private var textViewTitle: TextView? = null
    private var textViewContext: TextView? = null


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
        inflate(context, R.layout.button_settings, this)
        layout = findViewById(R.id.layout)
        imageView= findViewById(R.id.imageView)
        textViewTitle= findViewById(R.id.textViewTitle)
        textViewContext= findViewById(R.id.textViewContext)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SettingsButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SettingsButton, defStyle, 0)
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

    private fun setImageView(drawable: Drawable?) {
        imageView?.setImageDrawable(drawable)
    }

    private fun setTextViewTitle(text: String?) {
        textViewTitle?.text=text
    }

    private fun setTextViewContext(text: String?) {
        textViewContext?.text=text
    }

}