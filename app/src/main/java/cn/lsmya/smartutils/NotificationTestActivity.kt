package cn.lsmya.smartutils

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationManagerCompat
import cn.lsmya.smart.ktx.loge
import cn.lsmya.smart.ktx.singleClick
import cn.lsmya.smart.ktx.toast
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityNotificationTestBinding


class NotificationTestActivity : BaseVBActivity<ActivityNotificationTestBinding>() {

    override fun initUI() {
        super.initUI()
        val areNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()
        loge(areNotificationsEnabled)
        getBinding().btn.singleClick {
            if (!isNLServiceEnabled()) {
                startActivityForResult( Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), 1111);
            } else {
                toast("通知服务已开启");
                toggleNotificationListenerService();
            }
        }
    }

    /**
     * 切换通知监听器服务
     *
     * @param enable
     */
    fun toggleNotificationListenerService() {
        val pm = packageManager
        pm.setComponentEnabledSetting(
            ComponentName(applicationContext, NotifyService::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )

        pm.setComponentEnabledSetting(
            ComponentName(applicationContext, NotifyService::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
    }
    /**
     * 是否启用通知监听服务
     * @return
     */
    fun isNLServiceEnabled(): Boolean {
        val packageNames = NotificationManagerCompat.getEnabledListenerPackages(this)
        if (packageNames.contains(packageName)) {
            return true
        }
        return false
    }
}