package com.wxc.serialport.utils

import android.hardware.usb.UsbDevice
import android.util.Log

internal object LogUtils {
    fun e(msg: String?) {
        msg?.let {
            Log.e("打印服务", it)
        }
    }

    fun e(usbDevice: UsbDevice, msg: String?) {
        msg?.let {
            Log.e("打印服务", "${usbDevice.productName}  $it")
        }
    }
}