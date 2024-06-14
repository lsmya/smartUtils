package cn.lsmya.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import cn.lsmya.smart.base.BaseFragment
import org.koin.android.ext.android.getKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope
import java.lang.reflect.Method

/**
 * @Description: Databinding + ViewModel BaseFragment
 * @Author: loongwind
 * @CreateDate： 2020/8/13 7:29 AM
 *
 */
abstract class BaseBindingViewModelFragment<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BaseFragment(), OnSubscribeListener {
    val viewModel: VM by lazy {
        createViewModel()
    }

    internal lateinit var subscribeMethodMap: Map<Class<*>, Method>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //创建 ViewDataBinding 实例
        val binding = createDataBinding(inflater, container)
        //绑定当前 Fragment 生命周期
        binding.lifecycleOwner = this

        // 初始化数据绑定
        initDataBinding(binding)
        registerSubscribe()

        //返回布局 View 对象
        return binding.root
    }

    /**
     * 根据泛型 BINDING 创建 ViewDataBinding 实例
     */
    private fun createDataBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        return getBindingType(javaClass)// 获取泛型类型
            ?.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ) // 反射获取 inflate 方法
            ?.invoke(null, inflater, container, false) as BINDING // 通过反射调用 inflate 方法
    }

    private fun initDataBinding(binding: BINDING) {
        //RDF 默认自动绑定 vm。具体业务实现中在实际的视图 xml 文件中声明当前视图的 ViewModel 为
        // vm 即可自动进行绑定。
        binding.setVariable(BR.vm, viewModel)
    }

    /**
     *
     * @description 初始化 ViewModel 并自动进行绑定
     * @param
     * @return VM
     *
     */
    @OptIn(KoinInternalApi::class)
    private fun createViewModel(): VM {

        val scope: Scope?
        val owner: ViewModelStoreOwner?
        val vmStore: ViewModelStore?

        val activity = this.activity ?: throw Exception("Fragment Activity is null")
        if (isShareViewModel()) {
            scope = activity.getKoinScope()
            owner = activity
            vmStore = activity.viewModelStore
        } else {
            scope = getKoinScope()
            owner = this
            vmStore = this.viewModelStore
        }
        try {
            val viewModel = getViewModel(javaClass, scope, owner, vmStore) as VM
            viewModel.bind(this)
            return viewModel
        } catch (e: Exception) {
            throw e
        }
    }

    open fun onEvent(eventId: Int) {
    }

    override fun onSubscribe(event: Any) {
        val eventType = event.javaClass
        val method = subscribeMethodMap[eventType]
        if (!subscribe(event)) {
            method?.invoke(this, event)
        }
    }


    /**
     *
     * @description 是否保持 ViewModel。默认创建与当前 Fragment 生命周期绑定的 ViewModel。
     * 重写此方法返回 true，则创建与当前 Fragment 宿主 Activity 生命周期绑定的 ViewModel，与当前
     * Activity 绑定的其他 Fragment 可共享该 ViewMoel
     * @return true：保持 ViewModel 生命周期与宿主 Activity 同步，false：保持 ViewModel 与当前
     * Fragment 生命周期同步
     *
     */
    open fun isShareViewModel(): Boolean {
        return false
    }

    private fun registerSubscribe() {
        subscribeMethodMap = getSubscribeMethods(javaClass)
    }
}