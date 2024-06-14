package cn.lsmya.mvvm.bindingadapter

import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter {
    @BindingAdapter("colorString")
    @JvmStatic
    fun setColor(view: TextView, color: String?) {
        color?.let {
            view.setTextColor(Color.parseColor(it))
        }
    }

    @BindingAdapter("sizeInt")
    @JvmStatic
    fun setSizeInt(view: TextView, size: Float?) {
        size?.let {
            view.textSize = if (size == 0f) 15f else size
        }
    }

    @BindingAdapter("setBold")
    @JvmStatic
    fun setBold(view: TextView, isBold: Boolean) {
        if (isBold) {
            view.setTypeface(null, Typeface.BOLD)
        } else {
            view.setTypeface(null, Typeface.NORMAL)
        }
    }
}