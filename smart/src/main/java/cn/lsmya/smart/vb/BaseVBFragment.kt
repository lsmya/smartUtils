package cn.lsmya.smart.vb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.base.BaseFragment
import cn.lsmya.smart.utils.getBindingType

abstract class BaseVBFragment<VB : ViewBinding> : BaseFragment() {

    private var viewBinding: VB? = null
    override fun initUI() {

    }

    override fun initData() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = createDataBinding(inflater, container)
        return viewBinding?.root
    }
    private fun createDataBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getBindingType(javaClass)// 获取泛型类型
            ?.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java) // 反射获取 inflate 方法
            ?.invoke(null, inflater, container, false) as VB // 通过反射调用 inflate 方法
    }

    fun getBinding(): VB? {
        return viewBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}