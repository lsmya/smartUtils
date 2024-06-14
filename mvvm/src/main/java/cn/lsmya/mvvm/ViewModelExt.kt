package cn.lsmya.mvvm


import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import cn.lsmya.smart.extension.postEvent
import cn.lsmya.smart.extension.toast
import cn.lsmya.smart.model.ToastModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

/**
 *
 * @description BaseViewModel 扩展方法，自动bind ViewModel 数据变化提示信息
 * observe owner 为继承 BaseBindingViewModelActivity 的 Activity
 * @param context
 * @return
 *
 */
internal fun BaseViewModel.bind(activity: BaseBindingViewModelActivity<*, *>) {
    observe(activity) {
        activity.onSubscribe(it)
    }
}

/**
 *
 * @description BaseViewModel 扩展方法，自动bind ViewModel 数据变化提示信息
 * observe owner 为继承 BaseBindingViewModelFragment 的 Fragment
 * @param context
 * @return
 *
 */
internal fun BaseViewModel.bind(fragment: BaseBindingViewModelFragment<*, *>) {
    observe(fragment) {
        fragment.onSubscribe(it)
    }
}


private fun BaseViewModel.observe(owner: LifecycleOwner, onEvent: (Any) -> Unit) {
    // 订阅事件变化
    event.observe(owner) {
        event.value?.getValueIfNotHandled()?.let {
            onEvent(it)
        }
    }
}

internal fun BaseBindingViewModelActivity<*, *>.subscribe(event: Any): Boolean {
    if (!subscribe(this, event) {
            onEvent(it)
        }) {
        return if (event is BackEvent) {
            onBackPressed()
            true
        } else {
            false
        }
    }
    return true
}

internal fun BaseBindingViewModelFragment<*, *>.subscribe(event: Any): Boolean {
    return subscribe(context, event) {
        onEvent(it)
    }
}

private fun subscribe(context: Context?, event: Any, onEvent: (Int) -> Unit): Boolean {
    when (event) {
        is Int -> {
            onEvent(event)
            return false
        }

        is ToastRes -> context?.toast(event.msgRes)
        is ToastString -> context?.toast(event.msg)
        else -> return false
    }
    return true
}


fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, showToast = true)
}

fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = { hideLoading() },
    onStart: (() -> Unit)? = { showLoading() },
    onFinally: (() -> Unit)? = { hideLoading() },
    showToast: Boolean = true
): Job {
    return request(
        block = block,
        onError = {
            if (showToast) {
                it.message?.let { msg ->
                    if (it is SocketTimeoutException) {
                        ToastModel("网络连接超时").postEvent()
                    } else {
                        ToastModel(msg).postEvent()
                    }
                }
            }
            onError?.invoke(it)
        },
        onStart =
        if (onStart == null) {
            showLoading()
            null
        } else {
            onStart
        },
        onFinally =
        if (onFinally == null) {
            hideLoading()
            null
        } else onFinally
    )
}


fun LifecycleOwner.request(
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
fun ViewModel.request(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return viewModelScope.launch {
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