package cn.lsmya.smart.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import cn.lsmya.smart.ktx.launch
import com.google.android.material.appbar.MaterialToolbar
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.layout.api.RefreshLayout


abstract class BaseActivity : AppCompatActivity(), BaseInterface {

    /**
     * 为用于填充的statusBar控件设置height，优先级高于statusBarSetMarginTop
     */
    open fun statusBarSetHeight(): View? {
        return null
    }

    /**
     * 为用于填充的statusBar控件设置MarginTop
     */
    open fun statusBarSetMarginTop(): View? {
        return null
    }

    /**
     * 是否全屏
     */
    open fun isFullScreen(): Boolean {
        return false
    }

    /**
     * 设置状态栏字体颜色是否是暗色
     */
    open fun isStatusBarDark(): Boolean {
        return true
    }

    private val loading by lazy {
        XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    open fun onInitUiBefore() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        onInitUiBefore()
        initStatusBar()

        initUI()
        initData()
        val toolbar = obtainTitleBar(findViewById(Window.ID_ANDROID_CONTENT))
        toolbar?.setNavigationOnClickListener { onNavigationOnClickListener() }
    }

    /**
     * 设置沉浸式状态栏
     */
    private fun initStatusBar() {
        val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        //设置状态栏字体颜色
        WindowCompat.getInsetsController(window, rootView).isAppearanceLightStatusBars =
            isStatusBarDark()
        //设置沉浸式状态栏
        if (isFullScreen()) {
            statusBarSetHeight()?.let {
                ViewCompat.setOnApplyWindowInsetsListener(it) { view, windowInsets ->
                    val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
                    view.updateLayoutParams<ViewGroup.LayoutParams> {
                        height = insets.top
                    }
                    WindowInsetsCompat.CONSUMED
                }
            }
            if (statusBarSetHeight() == null) {
                statusBarSetMarginTop()?.let {
                    ViewCompat.setOnApplyWindowInsetsListener(it) { view, windowInsets ->
                        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
                        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            topMargin = insets.top
                        }
                        WindowInsetsCompat.CONSUMED
                    }
                }
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    open fun onNavigationOnClickListener() {
        finish()
    }

    /**
     * 递归获取 ViewGroup 中的 MaterialToolbar 对象
     */
    open fun obtainTitleBar(group: ViewGroup?): MaterialToolbar? {
        if (group == null) {
            return null
        }
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is MaterialToolbar) {
                return view
            }
            if (view is ViewGroup) {
                val titleBar = obtainTitleBar(view)
                if (titleBar != null) {
                    return titleBar
                }
            }
        }
        return null
    }

    /**
     * 是否需要点击空白区域隐藏键盘
     *
     * @return
     */
    protected open fun enableClickBlankCloseSoftKeyboard(): Boolean {
        return true
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

    /**
     * 解决软键盘与底部输入框冲突问题
     */
    protected open fun keyboardEnable(): Boolean {
        return true
    }

    fun <T> getList(
        isRefresh: Boolean = true,
        showLoading: Boolean = true,
        page: Int,
        pageSize: Int = 10,
        refreshLayout: RefreshLayout? = null,
        request: suspend ((page: Int, pageSize: Int) -> List<T>?)
    ) {
        launch(
            onStart = {
                if (showLoading) {
                    showLoading()
                }
            },
            block = {
                val list = request.invoke(page, pageSize)
                if (refreshLayout != null) {
                    if (isRefresh) {
                        if (list.isNullOrEmpty() || list.size < pageSize) {
                            refreshLayout.finishRefreshWithNoMoreData()
                        } else {
                            refreshLayout.finishRefresh(true)
                        }
                    } else {
                        if (list.isNullOrEmpty() || list.size < pageSize) {
                            refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            refreshLayout.finishLoadMore(true)
                        }
                    }
                }
                if (showLoading) {
                    hideLoading()
                }
            },
            onError = {
                if (isRefresh) {
                    refreshLayout?.finishRefresh(false)
                } else {
                    refreshLayout?.finishLoadMore(false)
                }
                if (showLoading) {
                    hideLoading()
                }
            },
            onFinally = {}
        )
    }

}