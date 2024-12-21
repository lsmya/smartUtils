package cn.lsmya.smart.ktx

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * 打开activity
 */
fun ComponentActivity.registerForActivityResult(callback: ActivityResultCallback<ActivityResult>) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)

/**
 * 打开activity
 */
fun Fragment.registerForActivityResult(callback: ActivityResultCallback<ActivityResult>) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)

/**
 * 申请单个权限
 */
fun ComponentActivity.registerForPermissionsResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

/**
 * 申请单个权限
 */
fun Fragment.registerForPermissionsResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

/**
 * 申请多个权限
 */
fun ComponentActivity.registerForMultiplePermissionsResult(callback: ActivityResultCallback<Map<String, Boolean>>) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

/**
 * 申请多个权限
 */
fun Fragment.registerForMultiplePermissionsResult(callback: ActivityResultCallback<Map<String, Boolean>>) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

/**
 * 调用系统相机拍照
 */
fun ComponentActivity.registerForTakePictureResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.TakePicture(), callback)

/**
 * 调用系统相机拍照
 */
fun Fragment.registerForTakePictureResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.TakePicture(), callback)

/**
 * 调用系统相机录像
 */
fun ComponentActivity.registerForTakeVideoResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.TakePicture(), callback)

/**
 * 调用系统相机录像
 */
fun Fragment.registerForTakeVideoResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.CaptureVideo(), callback)

/**
 * 选择一条内容
 * 图片：launcher.launch("image/\*")
 * 视频：launcher.launch("video/\*")
 * 音频：launcher.launch("audio/\*")
 */
fun ComponentActivity.registerForGetContentResult(callback: ActivityResultCallback<Uri?>) =
    registerForActivityResult(ActivityResultContracts.GetContent(), callback)

/**
 * 选择一条内容
 * 图片：launcher.launch("image/\*")
 * 视频：launcher.launch("video/\*")
 * 音频：launcher.launch("audio/\*")
 */
fun Fragment.registerForGetContentResult(callback: ActivityResultCallback<Uri?>) =
    registerForActivityResult(ActivityResultContracts.GetContent(), callback)

/**
 * 从通讯录获取联系人
 */
fun ComponentActivity.registerForPickContactResult(callback: ActivityResultCallback<Uri?>) =
    registerForActivityResult(ActivityResultContracts.PickContact(), callback)

/**
 * 从通讯录获取联系人
 */
fun Fragment.registerForPickContactResult(callback: ActivityResultCallback<Uri?>) =
    registerForActivityResult(ActivityResultContracts.PickContact(), callback)
