package cn.lsmya.smart.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 商品单价控件，带删除线
 */
class PriceTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context!!, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas) {
        paint.isStrikeThruText = true
        paint.isAntiAlias = true
        super.onDraw(canvas)
    }
}