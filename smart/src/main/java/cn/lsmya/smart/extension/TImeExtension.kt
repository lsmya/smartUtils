package cn.lsmya.smart.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Long.toDate(pattern: String): String {
    val date = Date(this)
    val format = SimpleDateFormat(pattern);
    return format.format(date)
}
fun Long.toDate(): String {
    if (this <= 0) {
        return ""
    }
    return this.toDate("yyyy-MM-dd HH:mm:ss")
}

fun String?.stampToDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
    return if ( this.isNullOrEmpty() ) -1L else SimpleDateFormat(pattern).parse(this)?.time ?: -1L
}