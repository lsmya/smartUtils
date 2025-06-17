package com.wxc.serialport.printer

import com.wxc.serialport.PrinterModel
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class NetworkPrinter(printer: PrinterModel) : BasePrinter(printer) {
    var socket: Socket? = null

    override fun connect() {
        val ip = printer.ip
        val port = printer.port
        if (socket != null && socket!!.isConnected) {
            return
        }
        val inetSocketAddress: SocketAddress = InetSocketAddress(ip, port)
        try {
            if (socket == null) {
                socket = Socket()
                socket!!.connect(inetSocketAddress, 5_000)
            } else {
                socket!!.close()
                socket = Socket()
                socket!!.connect(inetSocketAddress)
                socket!!.setSoTimeout(5_000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getConnectStatus(): Boolean {
        return socket != null && socket!!.isConnected
    }

    override fun sendCmd(bytes: ByteArray): Boolean {
        synchronized(this) {
            try {
                val stream = socket?.getOutputStream()
                stream?.write(bytes)
                return stream != null
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        }
        return false
    }

    override fun close(printer: PrinterModel) {
        socket?.close()
    }
}