package cn.lsmya.smart.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup


abstract class BaseFragment : Fragment(), BaseInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
    }

    private val loading by lazy {
        XPopup.Builder(requireContext())
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    /**
     * 显示加载loading动画
     */
    open fun showLoading() {
        if (!loading.isShow) {
            loading.show()
        }
    }

    /**
     * 隐藏加载loading动画
     */
    open fun hideLoading() {
        if (loading.isShow) {
            loading.dismiss()
        }
    }


}