package cn.lsmya.smart.ktx

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * 将图片转成base64字符串
 */
fun Bitmap?.bitmap2Base64(): String? {
    return this.bitmap2Byte()?.byte2Base64()
}

/**
 * 将图片转成byte数组
 */
fun Bitmap?.bitmap2Byte(): ByteArray? {
    if (this == null) return null
    val outputStream = ByteArrayOutputStream()
    //把bitmap100%高质量压缩 到 output对象里
    this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    return outputStream.toByteArray()
}

/**
 * 将图片byte数组转成base64字符串
 */
fun ByteArray?.byte2Base64(): String? {
    if (null == this) return null
    return Base64.encodeToString(this, Base64.DEFAULT)
}

/**
 * 将图片转换成Base64编码的字符串
 */
fun String?.imageToBase64(): String? {
    if (this.isNullOrEmpty()) {
        return null
    }
    var `is`: InputStream? = null
    val data: ByteArray?
    var result: String? = null
    try {
        `is` = FileInputStream(this)
        //创建一个字符流大小的数组。
        data = ByteArray(`is`.available())
        //写入数组
        `is`.read(data)
        //用默认的编码格式进行编码
        result = Base64.encodeToString(data, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (null != `is`) {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return result
}

/**
 * 将Base64编码转换为图片并保存到本地
 * @param path
 * @return true
 */
fun String?.base64ToFile(path: String?): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    val data = Base64.decode(this, Base64.NO_WRAP)
    for (i in data.indices) {
        if (data[i] < 0) {
            //调整异常数据
            data[i] = (data[i] + 256).toByte()
        }
    }
    val os: OutputStream?
    try {
        os = FileOutputStream(path)
        os.write(data)
        os.flush()
        os.close()
        return true
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return false
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}


/**
 * base64转为bitmap
 */
fun String?.base64ToBitmap(): Bitmap? {
    if (this.isNullOrEmpty()) {
        return null
    }
    val bytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}