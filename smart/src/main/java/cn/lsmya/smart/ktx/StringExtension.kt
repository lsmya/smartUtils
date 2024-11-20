package cn.lsmya.smart.ktx

import java.util.regex.Pattern

public fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

public fun CharSequence?.replaceAll(oldValue: String, newValue: String): String {
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
public inline fun <C, R> C?.ifNullOrEmpty(defaultValue: () -> R): R where C : CharSequence, C : R =
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