package com.example.customtext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomText : AppCompatTextView {
    constructor(context: Context)
            : super(context) {
    }
    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs){

        val typed = context.obtainStyledAttributes(attrs, R.styleable.CustomText)

        val size = typed.indexCount

        for (i in 0 until size) {
            when (typed.getIndex(i)) {
                R.styleable.CustomText_delimeter -> {
                    val delimeter = typed.getString(typed.getIndex(i)) ?: "-"
                    process(delimeter)
                }
            }
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr){
    }

    fun process(delimeter:String?){
        var one = text.substring(0,4)
        var two = text.substring(4,6)
        var three = text.substring(6)

        setText("$one $delimeter $two $delimeter $three")
    }

}
