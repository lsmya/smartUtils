package com.wxc.serialport

import android.hardware.usb.UsbDevice

data class PrinterModel(
    val mode: PrintMode,
    val ip: String = "",
    val port: Int = 0,
    val deviceId: String = "",
    val usbDevice: UsbDevice? = null
) {
    companion object {
        fun createUsbPrinter(device: UsbDevice): PrinterModel {
            return PrinterModel(mode = PrintMode.USB, usbDevice = device)
        }
    }
}