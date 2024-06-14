package cn.lsmya.smart.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * 实现了粘贴事件监听回调的 EditText
 */
open class ListenPasteEditTextTest : AppCompatEditText {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context,attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    var lisenter: ClipInterface? = null

    override fun onTextContextMenuItem(id: Int): Boolean {
        when(id) {
            //剪切复制黏贴
            android.R.id.cut -> lisenter?.onCut();
            android.R.id.copy -> lisenter?.onCopy();
            android.R.id.paste -> lisenter?.onPaste();
        }

        return super.onTextContextMenuItem(id)
    }
}

interface ClipInterface{
    fun onCut()
    fun onCopy()
    fun onPaste()
}