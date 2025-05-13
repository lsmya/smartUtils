package cn.lsmya.smart.ktx

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 打开软键盘
 *
 * @param mEditText 需要弹出软键盘的EditText
 */
fun Activity?.openKeyboard(mEditText: EditText) {
    if (this == null) return
    val insetsController = WindowCompat.getInsetsController(window, mEditText)
    insetsController.show(WindowInsetsCompat.Type.ime())
}

/**
 * 动态隐藏软键盘
 */
fun Activity?.hideSoftInput() {
    if (this == null) return
    val view = window.peekDecorView()
    if (view != null) {
        val inputManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManger.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * 判断当前软键盘是否打开
 *
 * @return true：打开；false：关闭
 */
fun Activity?.isSoftInputShow(): Boolean? {
    if (this == null) return null
    // 虚拟键盘隐藏 判断view是否为空
    val view = window.peekDecorView()
    if (view != null) {
        // 隐藏虚拟键盘
        val inputManger = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputManger.isActive && window.currentFocus != null
    }
    return false
}