package cn.lsmya.smart.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 控制、判断软键盘
 */
object KeyboardsUtils {
    /**
     * 打开软键盘
     *
     * @param mEditText 需要弹出软键盘的EditText
     * @param mContext  上下文
     */
    @JvmStatic
    fun openKeyboard(mEditText: EditText, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    @JvmStatic
    fun hideSoftInput(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputManger = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManger.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity 当前activity
     * @return true：打开；false：关闭
     */
    @JvmStatic
    fun isSoftInputShow(activity: Activity): Boolean {
        // 虚拟键盘隐藏 判断view是否为空
        val view = activity.window.peekDecorView()
        if (view != null) {
            // 隐藏虚拟键盘
            val inputManger = activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputManger.isActive && activity.window.currentFocus != null
        }
        return false
    }
}