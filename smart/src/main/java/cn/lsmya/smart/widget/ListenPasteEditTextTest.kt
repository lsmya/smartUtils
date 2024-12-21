package cn.lsmya.smart.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * 实现了粘贴事件监听回调的 EditText
 */
open class ListenPasteEditTextTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    var lisenter: OnClipInterface? = null

    override fun onTextContextMenuItem(id: Int): Boolean {
        when (id) {
            //剪切复制黏贴
            android.R.id.cut -> lisenter?.onCut();
            android.R.id.copy -> lisenter?.onCopy();
            android.R.id.paste -> lisenter?.onPaste();
        }

        return super.onTextContextMenuItem(id)
    }
}

interface OnClipInterface {
    fun onCut()
    fun onCopy()
    fun onPaste()
}