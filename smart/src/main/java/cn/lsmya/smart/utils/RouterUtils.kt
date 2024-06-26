package cn.lsmya.smart.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.therouter.TheRouter
import com.therouter.getApplicationContext
import com.therouter.router.Navigator

fun openPage(
    path: String,
    context: Context? = null,
    fragment: Fragment? = null,
    flag: Int? = null,
    requestCode: Int? = -1,
    bundle: (Navigator.() -> Unit)? = null
) {
    if (flag == null) {
        openPage(
            path,
            context = context,
            fragment = fragment,
            flags = intArrayOf(),
            bundle = bundle,
            requestCode = requestCode
        )
    } else {
        openPage(
            path,
            context = context,
            fragment = fragment,
            flags = intArrayOf(flag),
            bundle = bundle,
            requestCode = requestCode
        )
    }
}

fun openPage(
    path: String,
    context: Context? = null,
    fragment: Fragment? = null,
    flags: IntArray,
    requestCode: Int? = -1,
    bundle: (Navigator.() -> Unit)? = null
) {
    val navigator = TheRouter.build(path)
    flags.forEach {
        navigator.addFlags(it)
    }
    if (bundle != null) {
        navigator.bundle()
    }
    if (requestCode != null) {
        if (fragment != null) {
            navigator.navigation(fragment, requestCode)
        } else {
            navigator.navigation(context ?: getApplicationContext(), requestCode)
        }
    } else {
        navigator.navigation()
    }
}