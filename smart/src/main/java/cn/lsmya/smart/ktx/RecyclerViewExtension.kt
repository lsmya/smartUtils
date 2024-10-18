package cn.lsmya.smart.ktx

import android.content.Context
import android.view.View
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
