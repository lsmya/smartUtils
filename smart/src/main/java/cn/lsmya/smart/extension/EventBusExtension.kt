package cn.lsmya.smart.extension

import org.greenrobot.eventbus.EventBus

/**
 * Eventbus扩展函数
 */
fun Any.registerEventBus() {
    EventBus.getDefault().register(this)
}

fun Any.unregisterEventBus() {
    EventBus.getDefault().unregister(this)
}

fun Any.postEvent() {
    EventBus.getDefault().post(this)
}

fun Any.postStickyEvent() {
    EventBus.getDefault().postSticky(this)
}

fun clearSticky() {
    EventBus.getDefault().removeAllStickyEvents()
}

fun <T> Class<T>.removeSticky() {
    EventBus.getDefault().removeStickyEvent(this)
}

fun Any.removeSticky() {
    EventBus.getDefault().removeStickyEvent(this)
}