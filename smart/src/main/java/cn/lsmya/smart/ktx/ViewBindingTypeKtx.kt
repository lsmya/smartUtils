package cn.lsmya.smart.ktx

import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

internal fun Any.getBindingType(): Class<*>? {
    val superclass = javaClass.genericSuperclass
    if (superclass is ParameterizedType) {
        //返回表示此类型实际类型参数的 Type 对象的数组
        val actualTypeArguments = superclass.actualTypeArguments
        return actualTypeArguments.firstOrNull {
            // 判断是 Class 类型 且是 ViewDataBinding 的子类
            it is Class<*> && ViewBinding::class.java.isAssignableFrom(it)
        } as? Class<*>
    }
    return null
}