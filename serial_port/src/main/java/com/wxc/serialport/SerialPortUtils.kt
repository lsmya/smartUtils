package com.wxc.serialport

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager


class SerialPortUtils {
    companion object {
        const val ACTION_USB_PERMISSION: String = "com.android.example.USB_PERMISSION"
    }

    /**
     * 获取usb设备列表
     */
    fun getUsbDeviceList(context: Context): List<UsbDevice> {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        return usbManager?.getDeviceList()?.values?.toList() ?: arrayListOf()
    }

}