package cn.lsmya.smart.ktx

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/******************** Long ******************/
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
fun Long?.toDateTimeString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
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
 * 时间戳转换为**天**小时**分钟**秒**毫秒
 * @param precision 精度  0:天   1:小时   2:分钟   3:秒   4:毫秒
 */
fun Long?.toFitTimeSpan(precision: Int): String? {
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

/**
 * 获取传入时间0点时间
 */
fun Long.getCurrentZeroTime(): Long {
    val c = Calendar.getInstance()
    c.time = Date(this)
    c[Calendar.HOUR_OF_DAY] = 0
    c[Calendar.MINUTE] = 0
    c[Calendar.SECOND] = 0
    c[Calendar.MILLISECOND] = 0
    return c.timeInMillis
}

/**
 * 获取当前月最后一天最后一秒
 */
fun Long.getLastDayOfMonth(): Date {
    // 获取指定日期所在月的最后一天
    val calendar = Calendar.getInstance()
    // 设置指定日期
    calendar.time = Date(this)
    calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH]] = 1
    // 最后一天
    calendar.roll(Calendar.DATE, -1)
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}
/******************** String ******************/
/**
 * 日期转换为时间戳（毫秒）
 */
fun String?.toTimeLong(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
    return if (this.isNullOrEmpty()) -1L else getSimpleDateFormat(pattern).parse(this)?.time ?: -1L
}

/**
 * 日期转换为date
 */
fun String?.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return this.toTimeLong(pattern).toDate()
}

/**
 * 日期转换为指定格式的日期
 */
fun String?.toDateTimeString(
    outputPattern: String,
    pattern: String = "yyyy-MM-dd HH:mm:ss"
): String? {
    return this.toTimeLong(pattern).toDateTimeString(outputPattern)
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
fun String?.getDayIndexOfWeek(pattern: String = "yyyy-MM-dd HH:mm:ss"): Int? {
    val calendar = this.toCalendar(pattern) ?: return null
    return calendar.get(Calendar.DAY_OF_WEEK) - 1
}

/**
 * 根据日期获取是周几
 */
fun String?.getDayIndexOfWeekZh(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    val week = this.getDayIndexOfWeek(pattern) ?: return null
    return arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")[week]
}
/******************** Date ******************/
/**
 * date转换为日期
 */
fun Date?.toDateTimeString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? {
    return this?.time?.toDateTimeString(pattern)
}

/**
 * date转换为日期
 */
fun Date?.toCalendar(): Calendar? {
    if (this == null) return null
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

/**
 * 获取传入时间月份第一天
 */
fun Date.getFirstDayDateOfMonth(): Date {
    // 获取指定日期所在月的第一天
    val calendar = Calendar.getInstance()
    // 设置指定日期
    calendar.time = this
    calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH]] = 1
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}

/**
 * 判断日期是否为今日
 */
fun Date?.isToday(): Boolean {
    if (this == null) return false
    val today = Calendar.getInstance()
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar[Calendar.YEAR] == today[Calendar.YEAR] &&
            calendar[Calendar.MONTH] == today[Calendar.MONTH] &&
            calendar[Calendar.DAY_OF_MONTH] == today[Calendar.DAY_OF_MONTH]
}

/**
 * 判断日期是否为今年
 */
fun Date?.isThisYear(): Boolean {
    if (this == null) return false
    val today = Calendar.getInstance()
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar[Calendar.YEAR] == today[Calendar.YEAR]
}

/**
 * 判断输入日期是否小于今日
 */
fun Date?.isBeforeDay(): Boolean {
    if (this == null) return false
    // 获取今日日期（时间部分清零）
    // 创建一个Calendar实例，用于获取当前日期和时间
    val todayCalendar = Calendar.getInstance()
    // 将时、分、秒和毫秒设置为0
    todayCalendar[Calendar.HOUR_OF_DAY] = 0
    todayCalendar[Calendar.MINUTE] = 0
    todayCalendar[Calendar.SECOND] = 0
    todayCalendar[Calendar.MILLISECOND] = 0
    val today = todayCalendar.time
    // 判断是否小于今日
    return this.before(today)
}

/******************** 其他 ******************/
@SuppressLint("SimpleDateFormat")
private fun getSimpleDateFormat(pattern: String): SimpleDateFormat {
    return SimpleDateFormat(pattern)
}

/**
 * 获取今天00:00的时间戳
 */
fun getTodayZeroTime(): Long {
    return System.currentTimeMillis().getCurrentZeroTime()
}