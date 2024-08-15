package cn.lsmya.smartutils

import cn.lsmya.smart.extension.loadUrl
import cn.lsmya.smart.extension.loge
import cn.lsmya.smart.extension.singleClick
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding
import java.io.File

class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun isStatusBarDark(): Boolean {
        return true
    }

    override fun initUI() {
        super.initUI()
        getBinding().btn.singleClick {
            selectSingleImage {
                loge(it)
                val file = File(it)
                loge(file.name)
                getBinding().image.loadUrl(it)
            }
        }

    }
}