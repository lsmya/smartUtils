package cn.lsmya.smart.base

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import cn.lsmya.smart.R
import cn.lsmya.smart.extension.callback
import cn.lsmya.smart.extension.postEvent
import cn.lsmya.smart.extension.request
import cn.lsmya.smart.model.ToastModel
import cn.lsmya.smart.utils.CoilEngine
import cn.lsmya.smart.utils.ImageFileCompressEngine
import cn.lsmya.smart.utils.ImageFileCropEngine
import com.google.android.material.appbar.MaterialToolbar
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException

abstract class BaseActivity : AppCompatActivity(), BaseInterface {

    /**
     * 状态栏沉浸
     */
    private var mImmersionBar: ImmersionBar? = null

    /**
     * 状态栏字体深色模式
     */
    protected open val isStatusBarDarkFont: Boolean
        protected get() = true

    /**
     * 获取状态栏沉浸的配置对象
     */
    val statusBarConfig: ImmersionBar
        get() {
            if (mImmersionBar == null) {
                mImmersionBar = createStatusBarConfig()
            }
            return mImmersionBar!!
        }
    private val loading by lazy {
        XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarColor(getStatusBarColor())
            .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
            .statusBarDarkFont(isStatusBarDarkFont) // 指定导航栏背景颜色
            .navigationBarColor(android.R.color.white) // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
            .autoDarkModeEnable(true, 0.2f)
    }

    open fun getStatusBarColor(): Int = R.color.status_bar_color

    /**
     * 是否使用沉浸式状态栏
     */
    protected val isStatusBarEnabled: Boolean
        protected get() = true

    open fun onInitUiBefore() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitUiBefore()
        initUI()
        initData()
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled) {
            statusBarConfig.init()
        }
        val toolbar = obtainTitleBar(findViewById(Window.ID_ANDROID_CONTENT))
        toolbar?.setNavigationOnClickListener { onNavigationOnClickListener() }
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

    /**
     * 是否开启亮色状态栏
     */
    protected open fun enableLightMode(): Boolean {
        return true
    }

    /**
     * 选择图片(单选)
     */
    fun selectSingleImage(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofImage(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = {
                if (it.isNotEmpty()) {
                    callback?.invoke(it[0])
                }
            })
    }

    /**
     * 选择视频(单选)
     */
    fun selectSingleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofVideo(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = {
                if (it.isNotEmpty()) {
                    callback?.invoke(it[0])
                }
            })
    }

    /**
     * 选择图片(多选)
     */
    fun selectMultipleImage(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofImage(),
            selectionMode = SelectModeConfig.MULTIPLE,
            cancel = cancel,
            callback = callback,
        )
    }

    /**
     * 选择视频(多选)
     */
    fun selectMultipleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofVideo(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = callback
        )
    }

    /**
     * 选择图片、视频（可多选）
     */
    private fun pictureSelector(
        chooseMode: Int,
        selectionMode: Int,
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)? = null,
    ) {
        PictureSelector.create(this)
            .openGallery(chooseMode)
            .setSelectionMode(selectionMode)
            .setImageEngine(CoilEngine())
            .isDirectReturnSingle(true)
            .setCropEngine(ImageFileCropEngine())
            .setCompressEngine(ImageFileCompressEngine())
            .callback(cancel = cancel, callback = callback)
    }

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return launch(block = block, showToast = true)
    }

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null,
        showToast: Boolean = true
    ): Job {
        return request(
            block = block,
            onError = {
                if (showToast) {
                    it.message?.let { msg ->
                        if (it is SocketTimeoutException) {
                            ToastModel("网络连接超时").postEvent()
                        } else {
                            ToastModel(msg).postEvent()
                        }
                    }
                }
                onError?.invoke(it)
            },
            onStart =
            if (onStart == null) {
                showLoading()
                null
            } else {
                onStart
            },
            onFinally =
            if (onFinally == null) {
                hideLoading()
                null
            } else onFinally
        )
    }
}