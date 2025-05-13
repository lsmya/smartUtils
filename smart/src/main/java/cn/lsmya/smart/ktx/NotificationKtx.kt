package cn.lsmya.smart.ktx

import android.content.Context
import androidx.core.app.NotificationManagerCompat

/**
 * 是否有通知权限
 */
fun Context.notificationsEnabled(): Boolean {
    return NotificationManagerCompat.from(this).areNotificationsEnabled()
}