package cn.lsmya.smart.extension

import android.net.Uri
import cn.lsmya.smart.utils.ImageUriPathUtil
import com.luck.picture.lib.basic.PictureSelectionCameraModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.therouter.getApplicationContext

fun PictureSelectionModel.callback(
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)? = null,
) {
    this.forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>?) {
            result?.let {
                val list = ArrayList<String>()
                it.forEach { localMedia ->
                    val filePath: String = localMedia.availablePath
                    if (PictureMimeType.isContent(filePath)) {
                        if (localMedia.isCut || localMedia.isCompressed) {
                            list.add(filePath)
                        } else {
                            val uri = Uri.parse(filePath)
                            val path =
                                ImageUriPathUtil.getPathFromUri(getApplicationContext(), uri)
                            list.add(path)
                        }
                    } else {
                        list.add(filePath)
                    }
                }
                callback?.invoke(list)
            }
        }

        override fun onCancel() {
            cancel?.invoke()
        }

    })
}

fun PictureSelectionCameraModel.callback(
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)? = null,
) {
    this.forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>?) {
            result?.let {
                val list = ArrayList<String>()
                it.forEach { localMedia ->
                    val filePath: String = localMedia.availablePath
                    if (PictureMimeType.isContent(filePath)) {
                        if (localMedia.isCut || localMedia.isCompressed) {
                            list.add(filePath)
                        } else {
                            val uri = Uri.parse(filePath)
                            val path =
                                ImageUriPathUtil.getPathFromUri(getApplicationContext(), uri)
                            list.add(path)
                        }
                    } else {
                        list.add(filePath)
                    }
                }
                callback?.invoke(list)
            }
        }

        override fun onCancel() {
            cancel?.invoke()
        }

    })
}