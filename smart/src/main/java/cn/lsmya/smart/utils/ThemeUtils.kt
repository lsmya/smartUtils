package com.xskj.common.utils

import android.content.Context
import android.content.res.Configuration

object ThemeUtils {
    /**
     * 判断是否是深色模式
     * @param context
     * @return
     */
    fun isNightMode(context: Context): Boolean {
        try {
            return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        } catch (ex: Exception) {
        }
        return false
    }
}