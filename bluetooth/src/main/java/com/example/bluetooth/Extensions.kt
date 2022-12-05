package com.example.bluetooth

import android.widget.ImageButton
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment

fun Fragment.changeButtonColor(button: ImageButton, color: Int) {
    val drawable = button.drawable
    DrawableCompat.setTint(drawable, color) //устанавливаем цвет картинки
    button.setImageDrawable(drawable) //возвращаем цвет картинки в саму картинку
}