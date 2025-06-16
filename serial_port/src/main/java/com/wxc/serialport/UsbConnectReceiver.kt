package com.wxc.serialport

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class UsbConnectReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        // 获取扫码内容，这里的 "SCAN_RESULT" 是扫码枪提供的action，具体可能不同
        val action = intent.action
        Log.e("usb广播", "actionId：$action")
    }
}
