package cn.lsmya.smart.ktx

import android.util.Base64

/**
 * base64 编码
 */
fun String.base64EncodeToString(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}

/**
 * base64 解码
 */
fun String.base64DecodeToString(): String {
    return String(Base64.decode(this, Base64.NO_WRAP))
}