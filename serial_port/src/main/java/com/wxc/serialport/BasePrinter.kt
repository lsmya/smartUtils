package com.wxc.serialport

import android.content.Context

interface BasePrinter {

    fun connect(context: Context)
    fun close(printer: PrinterModel)

    fun getConnectStatus(): Boolean

    fun printString(content: String)
}