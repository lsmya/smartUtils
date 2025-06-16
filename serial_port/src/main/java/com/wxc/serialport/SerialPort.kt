package com.wxc.serialport

import android.util.Log
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class SerialPort(device: File, baudrate: Int, flags: Int) {
    /*
       * Do not remove or rename the field mFd: it is used by native method close();
       */
    private var mFd: FileDescriptor?
    private val mFileInputStream: FileInputStream?
    private val mFileOutputStream: FileOutputStream?

    val inputStream: InputStream?
        // Getters and setters
        get() = mFileInputStream

    val outputStream: OutputStream?
        get() = mFileOutputStream

    external fun close()

    init {
        mFd = open(device.getAbsolutePath(), baudrate, flags)
        if (mFd == null) {
            Log.e(TAG, "native open returns null")
            throw IOException()
        }
        mFileInputStream = FileInputStream(mFd)
        mFileOutputStream = FileOutputStream(mFd)
    }

    companion object {
        private const val TAG = "SerialPort"

        // JNI
        private external fun open(path: String?, baudrate: Int, flags: Int): FileDescriptor?

        init {
            System.loadLibrary("serial_port")
        }
    }
}
