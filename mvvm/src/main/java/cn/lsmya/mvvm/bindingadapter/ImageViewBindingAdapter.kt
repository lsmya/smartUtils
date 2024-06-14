package cn.lsmya.mvvm.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object ImageViewBindingAdapter {
    @BindingAdapter(value = ["imageUrl","placeholder","error"],requireAll = false)
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?, placeholder: Drawable?, error: Drawable?) {
        view.load(imageUrl) {
            crossfade(true)
            placeholder?.let {
                placeholder(it)
            }
            error?.let {
                error(it)
            }
        }

    }

}