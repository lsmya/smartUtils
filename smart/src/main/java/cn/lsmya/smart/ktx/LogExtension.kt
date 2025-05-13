package cn.lsmya.smart.ktx

import android.content.Intent
import android.util.Log

fun Any?.d() {
    log(Log.DEBUG, null, this, 4)
}

fun Any?.e() {
    log(Log.ERROR, null, this, 4)
}

fun Any?.i() {
    log(Log.INFO, null, this, 4)
}

fun Any?.v() {
    log(Log.VERBOSE, null, this, 4)
}

fun Any?.w() {
    log(Log.WARN, null, this, 4)
}

fun logd(any: Any?, tag: String?) {
    log(Log.DEBUG, tag, any, 5)
}

fun loge(any: Any?, tag: String? = null) {
    log(Log.ERROR, tag, any, 5)
}

fun logi(any: Any?, tag: String?) {
    log(Log.INFO, tag, any, 5)
}

fun logv(any: Any?, tag: String?) {
    log(Log.VERBOSE, tag, any, 5)
}

fun logw(any: Any?, tag: String?) {
    log(Log.WARN, tag, any, 5)
}

private fun log(method: Int, tag: String?, any: Any?, stackTraceIndex: Int) {
    any?.let {
        val mTag = tag.ifNullOrEmpty { "tag" }
        var logMsg = getLogMessage(it)
        if (logMsg.isNotNullOrEmpty()) {
            val stackTrace = Thread.currentThread().stackTrace[stackTraceIndex]
            val fileName = stackTrace.fileName
            val lineNumber = stackTrace.lineNumber
            val methodName = stackTrace.methodName
            val msgHead = buildString {
                append("\n╔====================(")
                append(fileName)
                append(":")
                append(lineNumber)
                append(")#")
                append(methodName)
                append("=============================================================================")
                append("\n ")
            }
            print(method, mTag, msgHead)
            val maxStrLength = 2001 - mTag.length
            while (logMsg!!.length > maxStrLength) {
                print(method, mTag, logMsg.substring(0, maxStrLength))
                logMsg = logMsg.substring(maxStrLength);
            }
            print(method, mTag, logMsg)
            val foot = buildString {
                append(" \n")
                append("╚====================(")
                append(fileName)
                append(":")
                append(lineNumber)
                append(")#")
                append(methodName)
                append("=============================================================================\n")
            }
            print(method, mTag, foot)
        }
    }
}

private fun print(method: Int, tag: String, msg: String) {
    when (method) {
        Log.DEBUG -> Log.d(tag, msg)
        Log.ERROR -> Log.e(tag, msg)
        Log.INFO -> Log.i(tag, msg)
        Log.VERBOSE -> Log.v(tag, msg)
        Log.WARN -> Log.w(tag, msg)
        else -> Log.wtf(tag, msg)
    }
}

private fun getLogMessage(any: Any): String? {
    return if (any is String || any is Int || any is Double || any is Float || any is Boolean) {
        any.toString()
    } else if (any is Intent) {
        val extras = any.extras
        if (extras != null) {
            val map = HashMap<String, Any?>()
            extras.keySet().map { map[it] = extras.get(it) }
            return map.toJsonByGson()
        } else {
            return null;
        }
    } else {
        any.toJson()
    }
}
