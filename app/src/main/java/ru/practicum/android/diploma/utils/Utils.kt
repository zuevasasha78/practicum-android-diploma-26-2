package ru.practicum.android.diploma.utils

import android.graphics.drawable.Drawable
import android.widget.TextView

object Utils {

    fun TextView.setImageTop(drawable: Drawable?) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
    }
}
