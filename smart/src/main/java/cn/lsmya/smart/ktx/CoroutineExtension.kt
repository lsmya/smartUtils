package cn.lsmya.smart.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import cn.lsmya.smart.SmartSdkConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * 协程
 */

//异步操作
fun async(block: suspend () -> Unit): Job {
    return GlobalScope.launch {
        block.invoke()
    }
}

fun FragmentActivity.ui(block: () -> Unit) {
    runOnUiThread(block)
}

fun Fragment.ui(block: () -> Unit) {
    activity?.ui(block)
}
