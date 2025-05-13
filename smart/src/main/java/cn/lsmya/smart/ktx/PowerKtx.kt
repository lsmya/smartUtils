package cn.lsmya.smart.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings

/**
 * 电源相关
 * 忽略电池优化，需要权限 <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
 */
@SuppressLint("BatteryLife")
private fun Context.isIgnoringBatteryOptimizations() {
    val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager?
    if (powerManager != null) {
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            try {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.setData(Uri.parse("package:$packageName"))
                startActivity(intent)
            } catch (_: Exception) {

            }
        }
    }
}