package cn.lsmya.smartutils

import android.content.Intent
import cn.lsmya.smart.extension.singleClick
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding


class MainActivity : BaseVBActivity<ActivityMainBinding>() {


    override fun initUI() {
        super.initUI()
        getBinding().btn.singleClick {
            val intent = Intent(this, TwoActivity::class.java)
            startActivity(intent)
        }
    }
}