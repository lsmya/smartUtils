package com.wxc.serialport

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbManager
import android.util.Log
import androidx.core.content.ContextCompat

class UsbPrinter(val printer: PrinterModel) : BasePrinter {
    companion object {
        const val ACTION_USB_PERMISSION: String = "com.android.example.USB_PERMISSION"
    }

    private var mUsbManager: UsbManager? = null
    private var mUsbDeviceConnection: UsbDeviceConnection? = null

    //UsbEndpoint:表示USB设备的单个端点。USB协议中,端点是用于发送和接收数据的逻辑
    private var printerEp: UsbEndpoint? = null

    override fun connect(context: Context) {
        val usbDevice = printer.usbDevice
        if (usbDevice == null) return

        mUsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        if (!mUsbManager!!.hasPermission(usbDevice)) {
            val filter = IntentFilter(ACTION_USB_PERMISSION)
            ContextCompat.registerReceiver(
                context,
                mUsbReceiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            val pi = PendingIntent.getBroadcast(
                context, 0, Intent(ACTION_USB_PERMISSION),
                PendingIntent.FLAG_IMMUTABLE
            )
            mUsbManager?.requestPermission(usbDevice, pi)
        } else {
            open(usbDevice)
        }
    }

    /**
     * 广播接收者，接收usb连接状态
     */
    private var mUsbReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (ACTION_USB_PERMISSION == intent.action) {
                synchronized(this) {
                    val device = intent.getParcelableExtra<UsbDevice?>(UsbManager.EXTRA_DEVICE)
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            open(device)
                        }
                    } else {
                        Log.e("权限", "permission denied for device $device")
                    }
                }
            }
        }
    }


    private fun open(device: UsbDevice) {
        var ep: UsbEndpoint? = null
        for (i in 0 until device.interfaceCount) {
            val usbInterface = device.getInterface(i);
            if (usbInterface.interfaceClass == 7) {
                val endpointCount = usbInterface.endpointCount
                for (n in 0 until endpointCount) {
                    val endpoint = usbInterface.getEndpoint(n)
                    if (endpoint.direction == UsbConstants.USB_DIR_OUT && endpoint.type == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        ep = endpoint //拿到UsbEndpoint
                        break
                    }
                }
            }
        }
        if (ep != null && mUsbManager != null) {
            //与USB设备建立连接
            mUsbDeviceConnection = mUsbManager?.openDevice(device)
            //拿到USB设备的端点
            printerEp = ep //拿到UsbEndpoint
        }
    }

    override fun getConnectStatus(): Boolean {
        return mUsbManager != null && mUsbManager!!.deviceList.values.any { it.deviceId == printer.usbDevice?.deviceId }
    }

    override fun close(printer: PrinterModel) {
        mUsbDeviceConnection?.close()
    }

    override fun printString(content: String) {
    }
    /**
     * usb写入
     *
     * @param bytes
     */
    fun write(bytes: ByteArray) {
        if (mUsbDeviceConnection != null) {
            try {
                mUsbDeviceConnection!!.claimInterface(usbInterface, true)
                //注意设定合理的超时值,以避免长时间阻塞
                val b = mUsbDeviceConnection!!.bulkTransfer(printerEp, bytes, bytes.size, USBPrinter.TIME_OUT)
                mUsbDeviceConnection!!.releaseInterface(usbInterface)
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

}