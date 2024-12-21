package cn.lsmya.smartutils

import android.app.Application
import cn.lsmya.smart.SmartSdkConfig
import cn.lsmya.smart.ktx.moshi
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.CoroutineExceptionHandler
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.converter.MoshiConverter
import rxhttp.wrapper.ssl.HttpsUtils

class MyApp : Application(){

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(
                R.color.background,
                R.color.textColor
            )
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(
                R.color.background,
                R.color.textColor
            )
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initNetwork()
        SmartSdkConfig.setCoroutineExceptionHandler(mCoroutineExceptionHandler)
    }

    private val mCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    /**
     * 初始化网络请求
     */
    private fun initNetwork() {
        val sslParams = HttpsUtils.getSslSocketFactory()
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//添加信任证书
            .hostnameVerifier { hostname, session -> true }//忽略host验证
            .build()
        RxHttpPlugins.init(okHttpClient)
            .setConverter(MoshiConverter.create(moshi))
            .setDebug(true, true)
    }
}