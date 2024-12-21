package cn.lsmya.smart.ktx

import android.content.Context
import android.net.Uri
import com.luck.picture.lib.basic.PictureSelectionCameraModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

internal fun PictureSelectionModel.callback(
    context: Context,
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
                            uri.toPath(context)?.let { path ->
                                list.add(path)
                            }
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

internal fun PictureSelectionCameraModel.callback(
    context: Context,
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
                            uri.toPath(context)?.let { path ->
                                list.add(path)
                            }
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