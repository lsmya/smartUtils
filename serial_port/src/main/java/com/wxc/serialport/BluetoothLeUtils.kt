package com.wxc.serialport

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.bluetooth.BluetoothLe
import androidx.bluetooth.ScanResult
import kotlinx.coroutines.flow.Flow

object BluetoothLeUtils {

    fun getPermission() =
        arrayListOf<String>().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_SCAN)
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            add(Manifest.permission.BLUETOOTH)
            add(Manifest.permission.BLUETOOTH_ADMIN)
        }.toTypedArray()

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun scanDevice(context: Context): Flow<ScanResult> {
        val bluetoothLe = BluetoothLe(context)
        return bluetoothLe.scan()
    }
}