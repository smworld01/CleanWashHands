package com.hands.clean.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hands.clean.R


class CustomButton : ConstraintLayout {
    var layout: ConstraintLayout? = null
    var imageView: ImageView? = null
    lateinit var customTextViewDescription: CustomTextViewDescription

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
        inflate(context, R.layout.custom_button, this);
        layout = findViewById(R.id.layout)
        imageView= findViewById(R.id.imageView)
        customTextViewDescription = findViewById(R.id.content)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomButton, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val image =
            typedArray.getDrawable(R.styleable.CustomButton_contentImage)
        setImageView(image)
        val title =
            typedArray.getString(R.styleable.CustomButton_contentTitle)
        setTextViewTitle(title)
        val context =
            typedArray.getString(R.styleable.CustomButton_contentContext)
        setTextViewContext(context)
        typedArray.recycle()
    }

    private fun setImageView(drawable: Drawable?) {
        imageView?.setImageDrawable(drawable)
    }

    fun setTextViewTitle(text: String?) {
        customTextViewDescription.setTextViewTitle(text)
    }

    fun setTextViewContext(text: String?) {
        customTextViewDescription.setTextViewContext(text)
    }

}