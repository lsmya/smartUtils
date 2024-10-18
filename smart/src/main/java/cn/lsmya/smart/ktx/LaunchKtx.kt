package cn.lsmya.smart.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import cn.lsmya.smart.SmartSdkConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun Fragment.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, onError = null, onStart = null, onFinally = null)
}

fun Fragment.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return lifecycleScope.launch {
        try {
            coroutineScope {
                onStart?.invoke()
                block()
            }
        } catch (e: Throwable) {
            if (isActive) {
                onError?.invoke(e)
                SmartSdkConfig.getCoroutineExceptionHandler()
                    ?.handleException(this.coroutineContext, e)
            }
        } finally {
            onFinally?.invoke()
        }
    }
}

fun FragmentActivity.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, onError = null, onStart = null, onFinally = null)
}

fun FragmentActivity.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return lifecycleScope.launch {
        try {
            coroutineScope {
                onStart?.invoke()
                block()
            }
        } catch (e: Throwable) {
            if (onError != null && isActive) {
                try {
                    onError(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            } else {
                e.printStackTrace()
            }
        } finally {
            onFinally?.invoke()
        }
    }
}