package cn.lsmya.smart.vb

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.base.BaseActivity
import cn.lsmya.smart.utils.getBindingType

abstract class BaseVBActivity<VB : ViewBinding> : BaseActivity() {

    private var binding: VB? = null
    fun getBinding(): VB {
        if (binding == null) {
            binding = createDataBinding()
        }
        return binding!!
    }

    override fun onInitUiBefore() {
        super.onInitUiBefore()
        setContentView(getBinding().root)
    }

    private fun createDataBinding(): VB {
        return getBindingType(javaClass) // 获取泛型类型
            ?.getMethod("inflate", LayoutInflater::class.java) // 反射获取 inflate 方法
            ?.invoke(null, LayoutInflater.from(this)) as VB // 通过反射调用 inflate 方法
    }

}