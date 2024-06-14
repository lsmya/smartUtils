package cn.lsmya.smart.extension

import android.content.res.Resources
import android.util.DisplayMetrics

/**
 * DisplayMetrics
 *
 * @return
 */
fun getDisplayMetrics(): DisplayMetrics = Resources.getSystem().displayMetrics

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Double.dp2px() = this * getDisplayMetrics().density + 0.5f
fun Float.dp2px() = this * getDisplayMetrics().density + 0.5f
fun Int.dp2px() = this * getDisplayMetrics().density + 0.5f
fun Long.dp2px() = this * getDisplayMetrics().density + 0.5f

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Double.px2dp() = this / getDisplayMetrics().density + 0.5f
fun Float.px2dp() = this / getDisplayMetrics().density + 0.5f
fun Int.px2dp() = this / getDisplayMetrics().density + 0.5f
fun Long.px2dp() = this / getDisplayMetrics().density + 0.5f

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
 */
fun Double.px2sp() = this / getDisplayMetrics().scaledDensity + 0.5f
fun Float.px2sp() = this / getDisplayMetrics().scaledDensity + 0.5f
fun Int.px2sp() = this / getDisplayMetrics().scaledDensity + 0.5f
fun Long.px2sp() = this / getDisplayMetrics().scaledDensity + 0.5f

/**
 * 根据手机的分辨率从 sp 的单位 转成为 px
 */
fun Double.sp2px() = this * getDisplayMetrics().scaledDensity + 0.5f
fun Float.sp2px() = this * getDisplayMetrics().scaledDensity + 0.5f
fun Int.sp2px() = this * getDisplayMetrics().scaledDensity + 0.5f
fun Long.sp2px() = this * getDisplayMetrics().scaledDensity + 0.5f