package cn.lsmya.smart

import kotlinx.coroutines.CoroutineExceptionHandler

object CoroutineExceptionConfig {
    private var mCoroutineExceptionHandler: CoroutineExceptionHandler? = null
    fun setCoroutineExceptionHandler(handler: CoroutineExceptionHandler) {
        mCoroutineExceptionHandler = handler
    }

    fun getCoroutineExceptionHandler(): CoroutineExceptionHandler? {
        return mCoroutineExceptionHandler
    }
}