package cn.lsmya.smart.ktx

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

fun String.format(vararg args: Any) = if (args.isNotEmpty()) String.format(this, *args) else this

fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun CharSequence?.replaceAll(oldValue: String, newValue: String): String {
    return if (this == null) {
        ""
    } else {
        var string = this.toString()
        while (string.contains(oldValue)) {
            string = string.replace(oldValue, newValue)
        }
        string
    }
}

@SinceKotlin("1.3")
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C?.ifNullOrEmpty(defaultValue: () -> R): R where C : CharSequence, C : R =
    if (isNullOrEmpty()) defaultValue() else this


/**
 * 是否是中文
 */
fun Char?.isChinese(): Boolean {
    if (this == null) return false
    val ub = Character.UnicodeBlock.of(this)
    return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
}

/**
 * 是否是英文
 */
fun String?.isEnglish(): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.matches("^[a-zA-Z]*".toRegex())
}

/**
 * 是否是中文
 */
fun String?.isChinese(): Boolean {
    if (this.isNullOrEmpty()) return false
    val regEx = "[\\u4e00-\\u9fa5]+"
    val p = Pattern.compile(regEx)
    val m = p.matcher(this)
    return m.find()
}

/**
 * 验证手机号码
 */
fun String?.checkPhoneNumber(): Boolean {
    if (this.isNullOrEmpty()) return false
    val pattern = Pattern.compile("^1[0-9]{10}$")
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 计算字符串的md5
 */
fun String?.encodeMd5(): String? {
    if (this.isNullOrEmpty()) return null
    try {
        //获取md5加密对象
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        //对字符串加密，返回字节数组
        val digest: ByteArray = instance.digest(this.toByteArray())
        val sb = StringBuffer()
        for (b in digest) {
            //获取低八位有效值
            val i = b.toInt() and 0xff
            //将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                //如果是一位的话，补0
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return null
}