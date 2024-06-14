package cn.lsmya.mvvm.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

object RecyclerViewBindingAdapter {
    @BindingAdapter("adapter")
    @JvmStatic
    fun <VH : RecyclerView.ViewHolder> setAdapter(
        recyclerView: RecyclerView,
        adapter: Adapter<VH>,
    ) {
        recyclerView.adapter = adapter
    }
}