package cn.lsmya.smart.ktx

import android.content.Context
import android.widget.TextView
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

fun Context.toastHint(textView: TextView?) {
    if (textView != null) {
        val hint = textView.hint
        if (hint.isNotNullOrEmpty()) {
            Toast.makeText(this, hint, Toast.LENGTH_SHORT).show()
        }
    }
}