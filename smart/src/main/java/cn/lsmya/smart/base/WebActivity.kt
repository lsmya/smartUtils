package cn.lsmya.smart.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.lsmya.smart.databinding.ActivityWebBinding
import cn.lsmya.smart.extension.intentExtras
import cn.lsmya.smart.vb.BaseVBActivity

class WebActivity : BaseVBActivity<ActivityWebBinding>() {

    companion object {
        fun start(context: Context, url: String?) {
            start(context, null, url)
        }

        fun start(context: Context, title: String?, url: String?) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }

        fun start(context: Context, title: Int, url: String?) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", context.getString(title))
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun createViewBinding(): ActivityWebBinding {
        return ActivityWebBinding.inflate(layoutInflater)
    }

    private val title by intentExtras("title", "")
    private val url by intentExtras("url", "")

    @SuppressLint("SetJavaScriptEnabled")
    override fun initUI() {
        getBinding().toolbar.title = title
        //设置支持js否则有些网页无法打开
        getBinding().webView.settings.javaScriptEnabled = true
        getBinding().webView.settings.domStorageEnabled = true
        getBinding().webView.settings.javaScriptCanOpenWindowsAutomatically = true
        getBinding().webView.webViewClient = mWebViewClient
        getBinding().webView.webChromeClient = mWebChromeClient
    }

    override fun initData() {
        //加载网络url
        getBinding().webView.loadUrl(url)
    }

    private val mWebViewClient = object : WebViewClient() {
        //监听到页面发生跳转的情况，默认打开web浏览器
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        //页面开始加载
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        //页面加载完成的回调方法
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url);
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            updateProgressBar(100)
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        //监听网页进度 newProgress进度值在0-100
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            updateProgressBar(newProgress)
        }

        //设置Activity的标题与 网页的标题一致
        override fun onReceivedTitle(view: WebView?, webTitle: String?) {
            super.onReceivedTitle(view, webTitle)
            if (title.isEmpty()) {
                getBinding().toolbar.title = webTitle
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //如果用户按的是返回键 并且WebView页面可以返回
        if (keyCode == KeyEvent.KEYCODE_BACK && getBinding().webView.canGoBack()) {
            //让WebView返回
            getBinding().webView.goBack()
            return true
        }
        //如果WebView不能返回 则调用默认的onKeyDown方法 退出Activity
        return super.onKeyDown(keyCode, event)
    }

    private fun updateProgressBar(progress: Int) {
        updateProgressBar(progress != 100, progress)
    }

    private fun updateProgressBar(isVisibility: Boolean, progress: Int) {
        getBinding().progressBar.let { progressBar ->
            progressBar.visibility =
                if (isVisibility && progress < 100) View.VISIBLE else View.GONE
            progressBar.progress = progress
        }
    }
}