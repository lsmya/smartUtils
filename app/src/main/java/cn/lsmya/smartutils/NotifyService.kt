package cn.lsmya.smartutils

import android.content.ComponentName
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import cn.lsmya.smart.ktx.loge
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NotifyService : NotificationListenerService() {
    /**
     * 发布通知
     * @param sbn 状态栏通知
     */
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.notification == null) return
        //消息内容
        var msgContent = ""
        if (sbn.notification.tickerText != null) {
            msgContent = sbn.notification.tickerText.toString()
        }

        //消息时间
        val time: String =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(Date(sbn.postTime))
        val a = String.format(
            Locale.getDefault(),
            "应用包名：%s\n消息内容：%s\n消息时间：%s\n",
            sbn.packageName, msgContent, time
        )
        loge(a)
    }

    /**
     * 通知已删除
     * @param sbn 状态栏通知
     */
    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }

    /**
     * 监听断开
     */
    override fun onListenerDisconnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通知侦听器断开连接 - 请求重新绑定
            requestRebind(
                ComponentName(
                    this,
                    NotificationListenerService::class.java
                )
            )
        }
    }


}