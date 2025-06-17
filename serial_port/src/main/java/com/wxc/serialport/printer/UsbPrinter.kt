package com.wxc.serialport.printer

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbInterface
import android.hardware.usb.UsbManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.wxc.serialport.PrinterModel

class UsbPrinter(val context: Context, printer: PrinterModel) : BasePrinter(printer) {
    companion object {
        private const val ACTION_USB_PERMISSION: String = "com.usb.printer.USB_PERMISSION"
    }

    private var mUsbManager: UsbManager? = null
    private var mUsbDeviceConnection: UsbDeviceConnection? = null
    private var mUsbInterface: UsbInterface? = null

    //UsbEndpoint:表示USB设备的单个端点。USB协议中,端点是用于发送和接收数据的逻辑
    private var mUsbEndpointOutput: UsbEndpoint? = null

    override fun connect() {
        val usbDevice = printer.usbDevice
        if (usbDevice == null) return
        mUsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        if (!mUsbManager!!.hasPermission(usbDevice)) {
            val permissionIntent =
                PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
            mUsbManager!!.requestPermission(usbDevice, permissionIntent);
            ContextCompat.registerReceiver(
                context,
                mUsbReceiver,
                IntentFilter(ACTION_USB_PERMISSION),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        } else {
            openDevice(usbDevice)
        }
    }

    /**
     * 广播接收者，接收usb连接状态
     */
    private var mUsbReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (ACTION_USB_PERMISSION == intent.action) {
                synchronized(this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        val device: UsbDevice? =
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                                intent.getParcelableExtra(
                                    UsbManager.EXTRA_DEVICE,
                                    UsbDevice::class.java
                                )
                            } else {
                                intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                            }
                        if (device != null) {
                            openDevice(device)
                        }
                    }
                }
            }
        }
    }


    private fun openDevice(device: UsbDevice) {
        var epOutput: UsbEndpoint? = null
        var mUsbInterface: UsbInterface? = null
        for (i in 0 until device.interfaceCount) {
            val usbInterface = device.getInterface(i);
            if (usbInterface.interfaceClass == 7) {
                val endpointCount = usbInterface.endpointCount
                for (n in 0 until endpointCount) {
                    val endpoint = usbInterface.getEndpoint(n)
                    if (endpoint.type == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if (endpoint.direction == UsbConstants.USB_DIR_OUT) {
                            epOutput = endpoint //拿到UsbEndpoint
                            mUsbInterface = usbInterface
                            break
                        }
                    }
                }
            }
        }
        if (epOutput != null && mUsbManager != null) {
            //与USB设备建立连接
            mUsbDeviceConnection = mUsbManager?.openDevice(device)
            //拿到USB设备的端点
            mUsbEndpointOutput = epOutput
            this.mUsbInterface = mUsbInterface
            if (getConnectStatus()) {
                // 在使用UsbInterface进行数据的写入写出之前，要申明对其的专有访问权限，防止通信混乱
                mUsbDeviceConnection!!.claimInterface(mUsbInterface, true)
            }
        }
    }

    override fun getConnectStatus(): Boolean {
        return mUsbManager != null && mUsbManager!!.deviceList.values.any { it.deviceId == printer.usbDevice?.deviceId }
    }

    override fun close(printer: PrinterModel) {
        mUsbDeviceConnection?.close()
        mUsbDeviceConnection = null
        context.unregisterReceiver(mUsbReceiver);
        mUsbManager = null;
    }

    /**
     * usb写入
     *
     * @param bytes
     */
    override fun sendCmd(bytes: ByteArray): Boolean {
        try {
            if (mUsbEndpointOutput != null) {
                //注意设定合理的超时值,以避免长时间阻塞
                val result =
                    mUsbDeviceConnection?.bulkTransfer(
                        mUsbEndpointOutput,
                        bytes,
                        bytes.size,
                        1_0000
                    )
                        ?: -1
                return result >= 0//大于0时代表成功
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}