package cn.lsmya.smart.extension

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

fun ComponentActivity.registerForActivityResult(callback: ActivityResultCallback<ActivityResult>) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)

fun Fragment.registerForActivityResult(callback: ActivityResultCallback<ActivityResult>) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)


fun ComponentActivity.registerForPermissionsResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

fun Fragment.registerForPermissionsResult(callback: ActivityResultCallback<Boolean>) =
    registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

fun ComponentActivity.registerForMultiplePermissionsResult(callback: ActivityResultCallback<Map<String, Boolean>>) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

fun Fragment.registerForMultiplePermissionsResult(callback: ActivityResultCallback<Map<String, Boolean>>) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)