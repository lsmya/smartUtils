package cn.lsmya.smart.extension

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Environment
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 获得屏幕宽度
 */
fun Context.getScreenWidth() :Int{
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}

/**
 * 获得屏幕高度(不包含状态栏、导航栏)
 */
fun Context.getScreenHeight() :Int{
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}

/**
 * 获得状态栏的高度
 */
fun Context.getStatusHeight(): Int {
    var statusHeight = -1
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val `object` = clazz.newInstance()
        val id = clazz.getField("status_bar_height")[`object`] as Int
        statusHeight = resources.getDimensionPixelSize(id)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return statusHeight
}

/**
 * 获取整块屏幕的高度
 */
fun Activity.getRealScreenHeight(): Int {
    var dpi = 0
    val display = windowManager.defaultDisplay
    val dm = DisplayMetrics()
    val c: Class<*>
    try {
        c = Class.forName("android.view.Display")
        val method =
            c.getMethod("getRealMetrics", DisplayMetrics::class.java)
        method.invoke(display, dm)
        dpi = dm.heightPixels
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return dpi
}

/**
 * 获取虚拟按键区域的高度
 */
fun Activity.getNavigationAreaHeight(): Int {
    val realScreenHeight = getRealScreenHeight()
    val screenHeight = getScreenHeight()
    return realScreenHeight - screenHeight
}

/**
 * 获取导航栏高度
 */
fun Context.getNavigationBarrH(): Int {
    val identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelOffset(identifier)
}

/**
 * 获取当前屏幕截图，包含状态栏
 */
fun Activity.snapShotWithStatusBar(): Bitmap? {
    val view = window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val bp = Bitmap.createBitmap(bmp, 0, 0, getScreenWidth(), getScreenHeight())
    view.destroyDrawingCache()
    return bp
}

/**
 * 获取当前屏幕截图，不包含状态栏
 */
fun Activity.snapShotWithoutStatusBar(): Bitmap? {
    val view = window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val frame = Rect()
    window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top
    var bp: Bitmap? = null
    bp = Bitmap.createBitmap(
        bmp,
        0,
        statusBarHeight,
        getScreenWidth(),
        getScreenHeight() - statusBarHeight
    )
    view.destroyDrawingCache()
    return bp
}

/**
 * 获得标题栏高度
 */
fun Activity.getTitleBarHeight(): Int {
    val contentTop =
        window.findViewById<View>(Window.ID_ANDROID_CONTENT)
            .top
    return contentTop - getStatusBarHeight()
}

/**
 * 获取通知栏高度，沉浸栏高度
 */
fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val obj = clazz.newInstance()
        val field = clazz.getField("status_bar_height")
        val temp = field[obj].toString().toInt()
        statusBarHeight = resources.getDimensionPixelSize(temp)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return statusBarHeight
}

/**
 * 保存bitmap，返回是否保存成功
 */
private  fun savePic(b: Bitmap, strFileName: String): Boolean {
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(strFileName)
        b.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.flush()
        fos.close()
        return true
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return false
}
/**
 * 获取屏幕分辨率
 */
fun getScreenDpi() = getDisplayMetrics().densityDpi


/**
 * 检查当前系统是否已开启暗黑模式
 */
fun Context.getDarkModeStatus(): Boolean {
    val mode: Int = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return mode == Configuration.UI_MODE_NIGHT_YES
}
/**
 * 根据毫秒获得格式化日期
 *
 * @param time   毫秒数
 * @param format 格式化字符串
 * @return 格式化后的字符串
 */
private fun getDate(time: Long, format: String): String {
    val date = Date(time)
    val formatter = SimpleDateFormat(format)
    return formatter.format(date)
}

/**
 * 是否存在sd卡
 */
private fun isExistsSD(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * 获得文件名
 */
private fun getFileName(context: Context): String? {
    val fileName =
        getDate(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".png"
    val localPath: String
    localPath = if (isExistsSD()) {
        context.externalCacheDir.toString() + File.separator + fileName
    } else {
        context.filesDir.toString() + fileName
    }
    return localPath
}
