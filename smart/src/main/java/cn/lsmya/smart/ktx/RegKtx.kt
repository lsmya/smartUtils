package cn.lsmya.smart.ktx

/**
 * 正则工具类
 */

/**
 * 手机号
 */
fun String.isMobile(): Boolean {
    return "^1[3456789]\\d{9}$".toRegex().matches(this)
}

/**
 * 隐藏手机中间四位
 */
fun String.hidePhoneCenterNumber(): String {
    return this.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "\$1****\$2")
}

/**
 *判断是否是纯数字
 *
 */
fun String.isAllNum(): Boolean {
    return "^\\d+\$".toRegex().matches(this)
}

/**
 * 是否是中文字符
 */
fun String.isChinese() = "^[\u4E00-\u9FA5]+$".toRegex().matches(this)

/**
 * 是否是英文
 */
fun String.isEnglish(): Boolean {
    return matches("^[a-zA-Z]*".toRegex())
}

/**
 * 是否是手机号
 */
fun String.isPhone() = "(\\+\\d+)?1[3456789]\\d{9}$".toRegex().matches(this)

/**
 * 是否是邮箱地址
 */
fun String.isEmail() = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?".toRegex().matches(this)

/**
 * 是否是身份证号码
 */
fun String.isIDCard() =
    "[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}\$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)".toRegex()
        .matches(this)
