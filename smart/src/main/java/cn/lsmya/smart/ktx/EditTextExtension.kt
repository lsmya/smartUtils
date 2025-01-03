package cn.lsmya.smart.ktx

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/**
 * 显示密码文本
 */
fun EditText.showPassword() {
    transformationMethod = HideReturnsTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 隐藏密码文本
 */
fun EditText.hidePassword() {
    transformationMethod = PasswordTransformationMethod.getInstance()
    setSelection(text.length)
}

fun EditText.value() = this.text.toString()

fun EditText?.switchPassword() {
    if (this == null) {
        return
    }
    if (this.transformationMethod is HideReturnsTransformationMethod) {
        this.hidePassword()
    } else if (this.transformationMethod is PasswordTransformationMethod) {
        this.showPassword()
    }
}