package cn.lsmya.smart.ktx

import android.util.Log

fun Any?.eJson() {
    this?.let {
        log(Log.ERROR, it.toJson())
    }
}

fun Any?.e() {
    log(Log.ERROR, this)
}

fun logd(any: Any?) {
    log(Log.DEBUG, any)
}

fun loge(any: Any?) {
    log(Log.ERROR, any)
}

fun logi(any: Any?) {
    log(Log.INFO, any)
}

fun logv(any: Any?) {
    log(Log.VERBOSE, any)
}

fun logw(any: Any?) {
    log(Log.WARN, any)
}

private fun log(method: Int, any: Any?) {
    any?.let {
        val logMsg =
            if (it is String || it is Int || it is Double || it is Float || it is Boolean) {
                it.toString()
            } else {
                it.toJson()
            }
        val stackTrace = Thread.currentThread().stackTrace[4]
        val tag = "log"
        val msg = String.format(
            "(%s:%d)#%s: ",
            stackTrace.fileName,
            stackTrace.lineNumber,
            stackTrace.methodName
        ) + logMsg
        when (method) {
            Log.DEBUG -> Log.d(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.VERBOSE -> Log.v(tag, msg)
            Log.WARN -> Log.w(tag, msg)
            else -> Log.wtf(tag, msg)
        }
    }
}