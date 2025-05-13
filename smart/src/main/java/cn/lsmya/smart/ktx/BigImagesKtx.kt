package cn.lsmya.smart.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader

/**
 * 显示大图
 */
fun Activity.showBigImage(position: Int, view: View?, images: List<String>?) {
    this.showImage(position, view, images)
}

/**
 * 显示大图
 */
fun Fragment.showBigImage(position: Int, view: View?, images: List<String>?) {
    requireContext().showImage(position, view, images)
}

private fun Context.showImage(position: Int, view: View?, images: List<String>?) {
    XPopup.Builder(this)
        .isDestroyOnDismiss(true)
        .asImageViewer(
            if (view is ImageView) view else null,
            position,
            images,
            null,
            SmartGlideImageLoader()
        )
        .show()
}