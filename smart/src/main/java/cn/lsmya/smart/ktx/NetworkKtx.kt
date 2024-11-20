package cn.lsmya.smart.ktx

import android.content.Context
import android.net.ConnectivityManager


/**
 * 判断网络是否连接
 */
fun Context.isConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetwork != null
}
