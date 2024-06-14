package cn.lsmya.smart.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String?) {
    if (msg.isNotNullOrEmpty()) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Context.toast(msg: Int?) {
    if (msg != null) {
        Toast.makeText(this, getString(msg), Toast.LENGTH_SHORT).show()
    }
}