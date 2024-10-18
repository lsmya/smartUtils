package cn.lsmya.smart.ktx

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import cn.lsmya.smart.utils.CoilEngine
import cn.lsmya.smart.utils.ImageFileCompressEngine
import cn.lsmya.smart.utils.ImageFileCropEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig

/**
 * 拍照
 */
fun Activity.openCameraOfImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    openCamera(
        context = this,
        chooseMode = SelectMimeType.ofImage(),
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 录制视频
 */
fun Activity.openCameraOfVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    openCamera(
        context = this,
        chooseMode = SelectMimeType.ofVideo(),
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 选择图片(单选)
 */
fun Activity.selectSingleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择视频(单选)
 */
fun Activity.selectSingleVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择图片(多选)
 */
fun Activity.selectMultipleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback,
    )
}

/**
 * 选择视频(多选)
 */
fun Activity.selectMultipleVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}


/**
 * 拍照
 */
fun Fragment.openCameraOfImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    openCamera(
        context = requireContext(),
        chooseMode = SelectMimeType.ofImage(),
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 录制视频
 */
fun Fragment.openCameraOfVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    openCamera(
        context = requireContext(),
        chooseMode = SelectMimeType.ofVideo(),
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 选择图片(单选)
 */
fun Fragment.selectSingleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择视频(单选)
 */
fun Fragment.selectSingleVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择图片(多选)
 */
fun Fragment.selectMultipleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback,
    )
}

/**
 * 选择视频(多选)
 */
fun Fragment.selectMultipleVideo(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 选择图片、视频（可多选）
 * @param chooseMode 选择类型  视频、图片
 * @param selectionMode 选择模式  单选、多选
 * @param enableCrop 是否裁剪
 * @param enableCompress 是否压缩
 * @param cancel 取消回调
 * @param callback 成功回调
 */
private fun pictureSelector(
    context: Context,
    chooseMode: Int,
    selectionMode: Int,
    enableCrop: Boolean,
    enableCompress: Boolean,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit),
) {
    PictureSelector.create(context)
        .openGallery(chooseMode)
        .isAutomaticTitleRecyclerTop(true)
        .setSelectionMode(selectionMode)
        .setImageEngine(CoilEngine())
        .isDirectReturnSingle(true)
        .apply {
            if (enableCrop) setCropEngine(ImageFileCropEngine())
            if (enableCompress) setCompressEngine(ImageFileCompressEngine())
        }
        .callback(context = context, cancel = cancel, callback = callback)
}

private fun openCamera(
    context: Context,
    chooseMode: Int,
    enableCrop: Boolean,
    enableCompress: Boolean,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    PictureSelector.create(context)
        .openCamera(chooseMode)
        .apply {
            if (enableCrop) setCropEngine(ImageFileCropEngine())
            if (enableCompress) setCompressEngine(ImageFileCompressEngine())
        }
        .callback(context = context, cancel = cancel, callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}
