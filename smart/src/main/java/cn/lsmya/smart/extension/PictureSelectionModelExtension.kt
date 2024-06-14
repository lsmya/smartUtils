package cn.lsmya.smart.extension

import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

fun PictureSelectionModel.callback(
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)? = null,
) {
    this.forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>?) {
            result?.let {
                val list = ArrayList<String>()
                callback?.invoke(list)
            }
        }

        override fun onCancel() {
            cancel?.invoke()
        }

    })
}