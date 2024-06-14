package cn.lsmya.smart.vb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.base.BaseFragment
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseVBFragment<VB : ViewBinding> : BaseFragment() {

    private var viewBinding: VB? = null

    private val inflateMethod: Method

    init {
        //获取泛型参数化类型信息
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        //由BaseViewBindingFragment<VB : ViewBinding>可知只有一个参数类型
        val clazz = parameterizedType.actualTypeArguments.first() as Class<*>
        //获取ViewBinding实现类中的inflate方法，如下：
        inflateMethod = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //获取viewBinding
        viewBinding = inflateMethod.invoke(null, inflater, container, false) as VB
        return viewBinding?.root
    }

    fun getBinding(): VB? {
        return viewBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}