package com.wxc.serialport.printer

import android.content.Context
import com.wxc.serialport.PrinterModel

class BluetoothPrinter(val context: Context, printer: PrinterModel) : BasePrinter(printer) {
    override fun connect() {

    }

    override fun close(printer: PrinterModel) {

    }

    override fun getConnectStatus(): Boolean {
        return false
    }

    override fun sendCmd(bytes: ByteArray): Boolean {
        return false
    }
}