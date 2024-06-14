package cn.lsmya.mvvm

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import cn.lsmya.smart.base.BaseActivity
import java.lang.reflect.Method

/**
 * @Description: Databinding + ViewModel BaseActivity
 * @Author: loongwind
 * @CreateDate： 2020/8/13 7:09 AM
 *
 */
open class BaseBindingViewModelActivity<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BaseActivity(), OnSubscribeListener {

    internal lateinit var subscribeMethodMap: Map<Class<*>, Method>
    override fun onInitUiBefore() {
        super.onInitUiBefore()
        //创建 ViewDataBinding 实例
        val binding = createDataBinding()
        //绑定当前 Activity 生命周期
        binding.lifecycleOwner = this
        //设置 View
        setContentView(binding.root)
        // 初始化数据绑定
        initDataBinding(binding)
        registerSubscribe()
    }

    /**
     * 根据泛型 BINDING 创建 ViewDataBinding 实例
     */
    private fun createDataBinding(): BINDING {
        return getBindingType(javaClass) // 获取泛型类型
            ?.getMethod("inflate", LayoutInflater::class.java) // 反射获取 inflate 方法
            ?.invoke(null, LayoutInflater.from(this)) as BINDING // 通过反射调用 inflate 方法
    }

    override fun initUI() {
    }

    override fun initData() {
    }

    //创建 ViewModel 变量并延迟初始化
    val viewModel: VM by lazy {
        createViewModel()
    }

    private fun initDataBinding(binding: BINDING) {
        //绑定 viewModel
        //绑定变量为 vm。
        // 具体业务实现中在实际的布局 xml 文件中声明当前视图的 ViewModel 变量为 vm 即可自动进行绑定。
        binding.setVariable(BR.vm, viewModel)

    }

    open fun onEvent(eventId: Int) {
        if (eventId == EVENT_BACK) {
            onBackPressed()
        }
    }

    override fun onSubscribe(event: Any) {
        val eventType = event.javaClass
        val method = subscribeMethodMap[eventType]
        if (!subscribe(event)) {
            method?.invoke(this, event)
        }
    }

    /**
     * @description 初始化 ViewModel 并自动进行绑定
     * @return VM ViewModel 实例对象
     */
    private fun createViewModel(): VM {
        try {
            //注入 ViewModel，并转换为 VM 类型
            val viewModel = injectViewModel() as VM
            viewModel.bind(this)
            return viewModel
        } catch (e: Exception) {
            // 抛出异常
            throw Exception("ViewModel is not inject", e)
        }
    }

    private fun registerSubscribe() {
        subscribeMethodMap = getSubscribeMethods(javaClass)
    }
}
