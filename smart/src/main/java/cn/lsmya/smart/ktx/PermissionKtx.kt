package cn.lsmya.smart.ktx

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * 检查是否获取所有权限
 */
fun Context.checkPermissionAllGranted(vararg permissions: String): Boolean {
    // 只要有一个权限没有被授予, 则直接返回 false
    return permissions.none {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }
}

/**
 * 检查是否有勾选了对话框中”Don’t ask again”的选项
 */
fun Activity.shouldShowRequestPermissionRationale(vararg permissions: String): Boolean {
    //只要有一个权限没有被授予,勾选了对话框中”Don’t ask again”的选项, 返回false
    return permissions.none {
        !ActivityCompat.shouldShowRequestPermissionRationale(this, it)
    }
}