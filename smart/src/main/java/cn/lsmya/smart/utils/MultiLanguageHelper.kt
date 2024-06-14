package cn.lsmya.smart.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.core.os.ConfigurationCompat
import java.util.Locale

/**
 * 多语言切换 Utils
 *
 * @author HeLiquan
 * @version 1.0.0
 * @date 2023/4/20 星期四
 * @see xxx
 * @since 1.0.0
 */
object MultiLanguageHelper {

    /**
     * Activity 更新语言资源
     */
    fun modifyContextLanguageConfig(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setAppLanguageApi24(context)
        } else {
            setAppLanguage(context)
        }
        return context
    }

    /**
     * 设置应用语言
     */
    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    fun setAppLanguage(context: Context) {
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        val locale = getSystemLocale()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, displayMetrics)
    }

    /**
     * 兼容 7.0 及以上
     */
    @TargetApi(Build.VERSION_CODES.N)
    private fun setAppLanguageApi24(context: Context): Context {
        val locale = getSystemLocale()
        val resource = context.resources
        val configuration = resource.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }

    /**
     * 获取当前系统语言，如未包含则默认英文
     */
    fun getSystemLocale(): Locale? {
        val local = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        } else {
            Locale.getDefault()
        }
        return local
    }

}
