package cn.lsmya.smart.ktx

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.lsmya.smart.R

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

fun Context?.toastSuccess(text: String) {
    if (this == null) {
        return
    }
    toast(this, text, R.drawable.ic_toast_success)
}

fun Context?.toastWarn(text: String) {
    if (this == null) {
        return
    }
    toast(this, text, R.drawable.ic_toast_success)
}

fun Context?.toastError(text: String) {
    if (this == null) {
        return
    }
    toast(this, text, R.drawable.ic_toast_success)
}

private fun toast(context: Context?, text: String, iconId: Int, gravity: Int = Gravity.CENTER) {
    if (context == null || TextUtils.isEmpty(text)) return
    val view = View.inflate(context, R.layout.custom_toast, null)
    val imageView = view.findViewById<ImageView>(R.id.toast_icon)
    imageView.setImageResource(iconId)
    imageView.visibility = View.VISIBLE
    val textView = view.findViewById<TextView>(R.id.toast_text)
    textView.text = text
    val mToast = Toast(context)
    mToast.setView(view)
    mToast.duration = Toast.LENGTH_SHORT
    mToast.setGravity(gravity, 0, 0)
    mToast.show()
}