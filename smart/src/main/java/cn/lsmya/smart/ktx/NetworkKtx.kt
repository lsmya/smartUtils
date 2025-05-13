package cn.lsmya.smart.ktx

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager


/**
 * 判断网络是否连接
 */
fun Context.isConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetwork != null
}

/**
 * 获取网络类型
 * 判断设备是连接的是wifi,还是连接的是2,3,4,5G网络
 */
fun Context.getNetworkType(): String {
    var net = "UNKNOWN"
    val cManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    cManager.getNetworkCapabilities(cManager.activeNetwork)?.let { cb ->
        if (cb.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            net = "WIFI"
        } else if (cb.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            net = getMobileNetworkType(cManager)
        }
    }
    return net
}

private fun getMobileNetworkType(cManager: ConnectivityManager): String {
    val networkInfo = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    if (networkInfo != null) {
        val networkType = networkInfo.subtype
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE -> "2G"
            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_HSPA -> "3G"
            TelephonyManager.NETWORK_TYPE_LTE -> "4G"
            TelephonyManager.NETWORK_TYPE_NR -> "5G"
            else -> "UNKNOWN"
        }
    }
    return "UNKNOWN"
}
