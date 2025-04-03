package cn.lsmya.smart.ktx

import androidx.viewpager2.widget.ViewPager2

inline fun ViewPager2.doOnItemSelected(crossinline block: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            block(position)
        }
    })
}