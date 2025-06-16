package com.wxc.serialport

import android.content.Context
import android.system.Os.socket
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class NetworkPrinter(val printer: PrinterModel) : BasePrinter {
    var socket: Socket? = null

    override fun connect(
        context: Context
    ) {
        val ip = printer.ip
        val port = printer.port
        if (socket != null && socket!!.isConnected) {
            return
        }
        val inetSocketAddress: SocketAddress = InetSocketAddress(ip, port)

        try {
            if (socket == null) {
                socket = Socket()
                socket!!.connect(inetSocketAddress, 1500)
            } else {
                socket!!.close()
                socket = Socket()
                socket!!.connect(inetSocketAddress)
                socket!!.setSoTimeout(1500)
            }
        } catch (e: Exception) {
        }
    }

    override fun getConnectStatus(): Boolean {
        return socket != null && socket!!.isConnected
    }

    override fun close(printer: PrinterModel) {
        socket?.close()
    }
}