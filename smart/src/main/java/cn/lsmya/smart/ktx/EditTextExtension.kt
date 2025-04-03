package cn.lsmya.smart.ktx

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.widget.EditText

/**
 * 显示密码文本
 */
fun EditText.showPassword() {
    // 设置编辑框的变换方法为 HideReturnsTransformationMethod，以显示密码文本
    transformationMethod = HideReturnsTransformationMethod.getInstance()
    // 将光标移动到文本末尾
    setSelection(text.length)
}

/**
 * 隐藏密码文本
 */
fun EditText.hidePassword() {
    // 将编辑文本的显示方式转换为密码形式，使输入的文本不可见
    transformationMethod = PasswordTransformationMethod.getInstance()
    // 设置光标位置到文本末尾，以便用户继续输入
    setSelection(text.length)
}

/**
 * 切换EditText控件中密码的显示与隐藏状态
 * 此函数适用于处理密码输入框的可见性切换，当密码为隐藏状态时调用showPassword()显示密码，
 * 当密码为显示状态时调用hidePassword()隐藏密码
 */
fun EditText?.switchPassword() {
    // 检查EditText对象是否为空，为空则直接返回，不做任何操作
    if (this == null) {
        return
    }
    // 判断当前EditText的文本转换方法是否为HideReturnsTransformationMethod类型，
    // 是则表明密码当前处于隐藏状态，调用hidePassword()方法将密码隐藏
    if (this.transformationMethod is HideReturnsTransformationMethod) {
        this.hidePassword()
    } else if (this.transformationMethod is PasswordTransformationMethod) {
        // 判断当前EditText的文本转换方法是否为PasswordTransformationMethod类型，
        // 是则表明密码当前处于显示状态，调用showPassword()方法将密码显示出来
        this.showPassword()
    }
}

/**
 * 获取输入框的内容
 */
inline val EditText.textString get() = text.toString()

/**
 * 将EditText的光标位置设置到文本的末尾
 */
fun EditText?.setSelectionToEnd() {
    this?.setSelection(text.length)
}

/**
 * 在EditText中添加监听Enter键的功能
 *
 * @param block 当用户按下Enter键后执行的代码块
 */
inline fun EditText.setOnEnterListener(crossinline block: () -> Unit) {
    setOnKeyListener { _, keyCode, event ->
        // 检查按键是否为Enter键且按键操作为释放
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            // 执行传入的代码块
            block()
            // 消费事件，防止其他监听器接收到此事件
            return@setOnKeyListener true
        }
        // 不消费事件，允许其他监听器处理此事件
        return@setOnKeyListener false
    }
}
