package cn.lsmya.smart.ktx

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun Long?.toDate(): Date? {
    if (this == null) {
        return null
    }
    return Date(this)
}

fun Long?.toDateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    if (this == null || this <= 0) {
        return null
    }
    val date = Date(this)
    val format = SimpleDateFormat(pattern);
    return format.format(date)
}

fun Long?.toCalendar(): Calendar? {
    if (this == null || this <= 0) {
        return null
    }
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(this)
    return calendar
}


fun String?.stampToDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
    return if (this.isNullOrEmpty()) -1L else SimpleDateFormat(pattern).parse(this)?.time ?: -1L
}

fun Date?.toDate(pattern: String): String? {
    return this?.time?.toDateString(pattern)
}

fun Date?.toDateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    return this?.time?.toDateString(pattern)
}

fun String?.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return this.stampToDate(pattern).toDate()
}

fun String?.toDateString(outPattern: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    return this.stampToDate(pattern).toDateString(outPattern)
}

fun String?.toCalendar(pattern: String = "yyyy-MM-dd HH:mm:ss"): Calendar? {
    val date = this.toDate(pattern) ?: return null
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

@RequiresApi(Build.VERSION_CODES.N)
fun String?.getWeek(pattern: String = "yyyy-MM-dd HH:mm:ss"): Int? {
    val calendar = this.toCalendar(pattern) ?: return null
    return calendar.get(Calendar.DAY_OF_WEEK) - 1
}

@RequiresApi(Build.VERSION_CODES.N)
fun String?.getWeekZh(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    val week = this.getWeek(pattern) ?: return null
    return arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")[week]
}

fun Long?.millis2FitTimeSpan(precision: Int): String? {
    if (this == null) {
        return null
    }
    var millis = this
    var precision = precision
    if (precision <= 0) return null
    precision = Math.min(precision, 5)
    val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
    if (millis == 0L) return 0.toString() + units[precision - 1]
    val sb = StringBuilder()
    if (millis < 0) {
        sb.append("-")
        millis = -millis
    }
    val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
    for (i in 0 until precision) {
        if (millis >= unitLen[i]) {
            val mode = millis / unitLen[i]
            millis -= mode * unitLen[i]
            sb.append(mode).append(units[i])
        }
    }
    return sb.toString()
}
