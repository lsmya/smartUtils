package cn.lsmya.smart.ktx

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * 倒计时的实现
 */
@ExperimentalCoroutinesApi
fun FragmentActivity.countDown(
    countTime: Int = 5,
    millisInFuture: Long = 1000,
    start: (scope: CoroutineScope) -> Unit,
    end: () -> Unit,
    error: ((Throwable) -> Unit)? = null,
    next: (time: Int) -> Unit
) {

    lifecycleScope.launch {
        // 在这个范围内启动的协程会在Lifecycle被销毁的时候自动取消
        flow {
            (countTime downTo 0).forEach {
                delay(millisInFuture)
                emit(it)
            }
        }.onStart {
            // 倒计时开始 ，在这里可以让Button 禁止点击状态
            start(this@launch)
        }.onCompletion {
            // 倒计时结束 ，在这里可以让Button 恢复点击状态
            end()
        }.catch {
            //错误
            error?.invoke(it)
        }.collect {
            // 在这里 更新值来显示到UI
            next(it)
        }

    }
}
