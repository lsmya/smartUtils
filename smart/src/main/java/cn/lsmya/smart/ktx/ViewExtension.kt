package cn.lsmya.smart.ktx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable

/**
 * 设置View的高度
 */
fun View.height(height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置View高度，限制在min和max范围之内
 * @param h
 * @param min 最小高度
 * @param max 最大高度
 */
fun View.limitHeight(h: Int, min: Int, max: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    when {
        h < min -> params.height = min
        h > max -> params.height = max
        else -> params.height = h
    }
    layoutParams = params
    return this
}

/**
 * 设置View的宽度
 */
fun View.width(width: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    params.width = width
    layoutParams = params
    return this
}

/**
 * 设置View宽度，限制在min和max范围之内
 * @param w
 * @param min 最小宽度
 * @param max 最大宽度
 */
fun View.limitWidth(w: Int, min: Int, max: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    when {
        w < min -> params.width = min
        w > max -> params.width = max
        else -> params.width = w
    }
    layoutParams = params
    return this
}

/**
 * 设置View的宽度和高度
 * @param width 要设置的宽度
 * @param height 要设置的高度
 */
fun View.widthAndHeight(width: Int, height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    params.width = width
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置View的margin
 * @param leftMargin 默认保留原来的
 * @param topMargin 默认是保留原来的
 * @param rightMargin 默认是保留原来的
 * @param bottomMargin 默认是保留原来的
 */
fun View.margin(leftMargin: Int = Int.MAX_VALUE, topMargin: Int = Int.MAX_VALUE, rightMargin: Int = Int.MAX_VALUE, bottomMargin: Int = Int.MAX_VALUE): View {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    if (leftMargin != Int.MAX_VALUE)
        params.leftMargin = leftMargin
    if (topMargin != Int.MAX_VALUE)
        params.topMargin = topMargin
    if (rightMargin != Int.MAX_VALUE)
        params.rightMargin = rightMargin
    if (bottomMargin != Int.MAX_VALUE)
        params.bottomMargin = bottomMargin
    layoutParams = params
    return this
}
/**
 * 切换View的可见性
 */
fun View.toggleVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

/**
 * 可见性相关
 */
fun View.isGone() = this.visibility == View.GONE

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE


fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.disable() {
    this?.isEnabled = false
}

fun View?.enable() {
    this?.isEnabled = true
}

/**
 * 设置界面隐藏显示
 *
 * @param boolean true时显示控件，false时隐藏控件
 */
fun View?.visibleOrGone(boolean: Boolean) {
    if (boolean) {
        this.visible()
    } else {
        this.gone()
    }
}

/**
 * @param boolean true时显示控件，false时隐藏控件
 */
fun View?.visibleOrInvisible(boolean: Boolean) {
    if (boolean) {
        this.visible()
    } else {
        this.invisible()
    }
}
/**
 * @param boolean 隐藏控件
 */
fun View?.invisibleOrGone(boolean: Boolean) {
    if (boolean) {
        this.visible()
    } else {
        this.invisible()
    }
}
/**
 * 通过布局文件获取[View]实例
 *
 * @param layoutId
 * @param root
 */
fun Context?.view(layoutId: Int, root: ViewGroup? = null, attachRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(layoutId, root, attachRoot)
}

inline fun <T : View> T.singleClick(time: Long = 500, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

//兼容点击事件设置为this的情况
fun <T : View> T.singleClick(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

/**
 * 返回布局文件获取到的View实例
 *
 * @param layoutId
 * @param root
 * @param attachRoot
 */
fun LayoutInflater?.view(
    layoutId: Int,
    root: ViewGroup? = null,
    attachRoot: Boolean = false
): View? {
    return this?.inflate(layoutId, root, attachRoot)
}

