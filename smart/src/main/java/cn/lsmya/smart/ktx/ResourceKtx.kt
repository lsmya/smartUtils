package cn.lsmya.smart.ktx

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * R.color.* 转 color
 */
fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

/**
 * #FFFFFF 转 color
 */
fun String.color(color: String) = Color.parseColor(color)