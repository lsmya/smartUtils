package cn.lsmya.smart.ktx

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView扩展函数
 */
fun <T : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder> RecyclerView.init(
    context: Context,
    adapter: T
): T {
    layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
    overScrollMode = View.OVER_SCROLL_NEVER
    setHasFixedSize(true)
    return adapter
}

fun <T : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder> RecyclerView.init(
    context: Context,
    spanCount: Int,
    adapter: T
): T {
    layoutManager = GridLayoutManager(context, spanCount)
    this.adapter = adapter
    overScrollMode = View.OVER_SCROLL_NEVER
    setHasFixedSize(true)
    return adapter
}

fun <T : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder> RecyclerView.initStaggeredGrid(
    spanCount: Int,
    adapter: T
): T {
    layoutManager =
        StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }
    this.adapter = adapter
    overScrollMode = View.OVER_SCROLL_NEVER
    setHasFixedSize(true)
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //防止第一行到顶部有空白区域
            (layoutManager as StaggeredGridLayoutManager).invalidateSpanAssignments()
        }
    })
    return adapter
}

/**
 * 设置分割线
 * @param color 分割线的颜色，默认是#DEDEDE
 * @param size 分割线的大小，默认是1px
 * @param isReplace 是否覆盖之前的ItemDecoration，默认是true
 *
 */
fun RecyclerView.divider(color: Int = Color.parseColor("#DEDEDE"), size: Int = 0.5.dp2px().toInt(), isReplace: Boolean = true): RecyclerView {
    val decoration = DividerItemDecoration(context, orientation)
    decoration.setDrawable(GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.RECTANGLE
        setSize(size, size)
    })
    if(isReplace && itemDecorationCount>0){
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
    return this
}

inline val RecyclerView.orientation
    get() = if (layoutManager == null) -1 else layoutManager.run {
        when (this) {
            is LinearLayoutManager -> orientation
            is GridLayoutManager -> orientation
            is StaggeredGridLayoutManager -> orientation
            else -> -1
        }
    }