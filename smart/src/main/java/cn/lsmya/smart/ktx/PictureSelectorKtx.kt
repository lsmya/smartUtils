package cn.lsmya.smart.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import cn.lsmya.smart.utils.CoilEngine
import cn.lsmya.smart.utils.ImageFileCompressEngine
import cn.lsmya.smart.utils.ImageFileCropEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener

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
 * 录制音频
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Activity.openRecordOfAudio(
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    PictureSelector.create(this)
        .openCamera(SelectMimeType.ofAudio())
        .apply {
            if (onReOnRecordAudioInterceptListener == null) {
                setRecordAudioInterceptListener { fragment, requestCode ->
                    val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                    fragment.startActivityForResult(intent, requestCode)
                }
            } else {
                setRecordAudioInterceptListener(onReOnRecordAudioInterceptListener)
            }
        }
        .callback(context = this, cancel = cancel, callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择图片(单选)
 * @param enableCrop 是否裁剪
 * @param enableCompress 是否压缩
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Activity.selectSingleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    isDisplayCamera: Boolean,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
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
    isDisplayCamera: Boolean,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择音频(单选)
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Activity.selectSingleAudio(
    isDisplayCamera: Boolean = false,
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofAudio(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = false,
        enableCompress = false,
        isDisplayCamera = isDisplayCamera,
        onReOnRecordAudioInterceptListener = onReOnRecordAudioInterceptListener,
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
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
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
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 选择音频(多选)
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Activity.selectMultipleAudio(
    isDisplayCamera: Boolean = false,
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit),
) {
    pictureSelector(
        context = this,
        chooseMode = SelectMimeType.ofAudio(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = false,
        enableCompress = false,
        isDisplayCamera = isDisplayCamera,
        onReOnRecordAudioInterceptListener = onReOnRecordAudioInterceptListener,
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
 * 录制音频
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Fragment.openRecordOfAudio(
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    PictureSelector.create(this)
        .openCamera(SelectMimeType.ofAudio())
        .apply {
            if (onReOnRecordAudioInterceptListener == null) {
                setRecordAudioInterceptListener { fragment, requestCode ->
                    val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                    fragment.startActivityForResult(intent, requestCode)
                }
            } else {
                setRecordAudioInterceptListener(onReOnRecordAudioInterceptListener)
            }
        }
        .callback(context = requireContext(), cancel = cancel, callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择图片(单选)
 */
fun Fragment.selectSingleImage(
    enableCrop: Boolean = false,
    enableCompress: Boolean = false,
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
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
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
        cancel = cancel,
        callback = {
            if (it.isNotEmpty()) {
                callback.invoke(it[0])
            }
        })
}

/**
 * 选择音频(单选)
 */
fun Fragment.selectSingleAudio(
    isDisplayCamera: Boolean = false,
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((String) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofAudio(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = false,
        enableCompress = false,
        isDisplayCamera = isDisplayCamera,
        onReOnRecordAudioInterceptListener = onReOnRecordAudioInterceptListener,
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
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofImage(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
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
    isDisplayCamera: Boolean = false,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofVideo(),
        selectionMode = SelectModeConfig.SINGLE,
        enableCrop = enableCrop,
        enableCompress = enableCompress,
        isDisplayCamera = isDisplayCamera,
        cancel = cancel,
        callback = callback
    )
}

/**
 * 选择音频(多选)
 * @param cancel 取消回调
 * @param callback 选择完成回调
 */
fun Fragment.selectMultipleAudio(
    isDisplayCamera: Boolean = false,
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit),
) {
    pictureSelector(
        context = requireContext(),
        chooseMode = SelectMimeType.ofAudio(),
        selectionMode = SelectModeConfig.MULTIPLE,
        enableCrop = false,
        enableCompress = false,
        isDisplayCamera = isDisplayCamera,
        onReOnRecordAudioInterceptListener = onReOnRecordAudioInterceptListener,
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
 * @param isDisplayCamera 是否显示相机
 * @param cancel 取消回调
 * @param callback 成功回调
 */
private fun pictureSelector(
    context: Context,
    chooseMode: Int,
    selectionMode: Int,
    enableCrop: Boolean,
    enableCompress: Boolean,
    isDisplayCamera: Boolean,
    onReOnRecordAudioInterceptListener: OnRecordAudioInterceptListener? = null,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit),
) {
    PictureSelector.create(context)
        .openGallery(chooseMode)
        .isAutomaticTitleRecyclerTop(true)
        .setSelectionMode(selectionMode)
        .isDisplayCamera(isDisplayCamera)
        .setImageEngine(CoilEngine())
        .isDirectReturnSingle(true)
        .apply {
            if (enableCrop) setCropEngine(ImageFileCropEngine())
            if (enableCompress) setCompressEngine(ImageFileCompressEngine())
            if (chooseMode == SelectMimeType.ofAudio()) {
                if (onReOnRecordAudioInterceptListener == null) {
                    setRecordAudioInterceptListener { fragment, requestCode ->
                        val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                        fragment.startActivityForResult(intent, requestCode)
                    }
                } else {
                    setRecordAudioInterceptListener(onReOnRecordAudioInterceptListener)
                }
            }
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