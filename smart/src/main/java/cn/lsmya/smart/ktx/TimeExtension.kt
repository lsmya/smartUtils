package cn.lsmya.smart.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * 时间戳（毫秒）转换为date
 */
fun Long?.toDate(): Date? {
    if (this == null) {
        return null
    }
    return Date(this)
}

/**
 * 时间戳（毫秒）转换为日期
 */
fun Long?.toDateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    if (this == null || this <= 0) {
        return null
    }
    val date = Date(this)
    val format = getSimpleDateFormat(pattern);
    return format.format(date)
}

/**
 * 时间戳（毫秒）转换为Calendar
 */
fun Long?.toCalendar(): Calendar? {
    if (this == null || this <= 0) {
        return null
    }
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(this)
    return calendar
}

/**
 * 日期转换为时间戳（毫秒）
 */
fun String?.stampToDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
    return if (this.isNullOrEmpty()) -1L else getSimpleDateFormat(pattern).parse(this)?.time ?: -1L
}

/**
 * 日期转换为date
 */
fun String?.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return this.stampToDate(pattern).toDate()
}

/**
 * 日期转换为指定格式的日期
 */
fun String?.toDateString(outPattern: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    return this.stampToDate(pattern).toDateString(outPattern)
}

/**
 * 日期转换为Calendar
 */
fun String?.toCalendar(pattern: String = "yyyy-MM-dd HH:mm:ss"): Calendar? {
    val date = this.toDate(pattern) ?: return null
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

/**
 * 根据日期获取是一周中的第几天（周日为第一天）
 */
fun String?.getWeek(pattern: String = "yyyy-MM-dd HH:mm:ss"): Int? {
    val calendar = this.toCalendar(pattern) ?: return null
    return calendar.get(Calendar.DAY_OF_WEEK) - 1
}

/**
 * 根据日期获取是周几
 */
fun String?.getWeekZh(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    val week = this.getWeek(pattern) ?: return null
    return arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")[week]
}

/**
 * date转换为日期
 */
fun Date?.toDateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    return this?.time?.toDateString(pattern)
}

/**
 * 时间戳转换为**天**小时**分钟**秒**毫秒
 */
fun Long?.millis2FitTimeSpan(precision: Int): String? {
    if (this == null) {
        return null
    }
    var millis = this
    var mPrecision = precision
    if (precision > 4) {
        mPrecision = 4
    }
    if (mPrecision < 0) return null
    val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
    if (millis <= 0L) return 0.toString() + units[mPrecision]
    val sb = StringBuilder()
    val unitArray = intArrayOf(86400000, 3600000, 60000, 1000, 1)
    mPrecision += 1
    for (i in 0 until mPrecision) {
        if (millis >= unitArray[i]) {
            val mode = millis / unitArray[i]
            millis -= mode * unitArray[i]
            sb.append(mode).append(units[i])
        }
    }
    return sb.toString()
}

@SuppressLint("SimpleDateFormat")
private fun getSimpleDateFormat(pattern: String): SimpleDateFormat {
    return SimpleDateFormat(pattern)
}