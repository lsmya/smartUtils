package cn.lsmya.smart.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerview分割线
 */
class ItemDecoration @JvmOverloads constructor(
    private val vertical: Int,
    private val horizontal: Int = 0
) :
    RecyclerView.ItemDecoration() {
    private var spanCount = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager is GridLayoutManager) {
            if (spanCount == -1) {
                spanCount =
                    (parent.layoutManager as GridLayoutManager).spanCount
            }
            val position = parent.getChildAdapterPosition(view)
            /* 绘制垂直边距 */
            if (position >= spanCount) { //第一行之外绘制垂直边距
                outRect.top = vertical
            }
            /* 绘制水平边距 */
            val pInLine: Int = position % spanCount // 行内位置
            if (pInLine == 0) {
                outRect.left = 0
            } else {
                outRect.left = (pInLine * 1f * horizontal / spanCount).toInt()
            }
            outRect.right = ((spanCount - pInLine - 1) * 1f * horizontal / spanCount).toInt()
        } else if (parent.layoutManager is LinearLayoutManager) {
            val position = parent.getChildAdapterPosition(view)
            if (position == 0) {
                outRect.top = 0
            } else {
                outRect.top = horizontal
            }
        }
    }
}
