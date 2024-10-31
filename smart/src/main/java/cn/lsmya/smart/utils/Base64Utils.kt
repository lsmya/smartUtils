package cn.lsmya.smart.utils

import android.util.Base64
import java.io.UnsupportedEncodingException

/**
 * base64加解密
 */
object Base64Utils {
    /**
     * 编码
     *
     * @param message 需编码的信息
     * @throws UnsupportedEncodingException
     */
    @kotlin.Throws(UnsupportedEncodingException::class)
    fun encodeToString(message: String): String {
        return Base64.encodeToString(message.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * 解码
     *
     * @param encodeWord 编码后的内容
     * @throws UnsupportedEncodingException
     */
    @kotlin.Throws(UnsupportedEncodingException::class)
    fun decodeToString(encodeWord: String?): String {
        return String(Base64.decode(encodeWord, Base64.NO_WRAP))
    }
}