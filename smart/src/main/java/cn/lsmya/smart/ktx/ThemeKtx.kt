package cn.lsmya.smart.ktx

import android.content.Context
import android.content.res.Configuration

/**
 * 判断是否是深色模式
 * @return
 */
fun Context?.isNightMode(): Boolean {
    if (this == null) return false
    try {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    } catch (ex: Exception) {
    }
    return false
}