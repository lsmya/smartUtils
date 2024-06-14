package cn.lsmya.smart.vb

import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.base.BaseActivity

abstract class BaseVBActivity<VB : ViewBinding> : BaseActivity() {

    private var binding: VB? = null
    fun getBinding(): VB {
        if (binding == null) {
            binding = createViewBinding()
        }
        return binding!!
    }

    abstract fun createViewBinding(): VB
    override fun onInitUiBefore() {
        super.onInitUiBefore()
        setContentView(getBinding().root)
    }

}