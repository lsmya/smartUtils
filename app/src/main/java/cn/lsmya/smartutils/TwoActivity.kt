package cn.lsmya.smartutils

import cn.lsmya.smart.ktx.singleClick
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityTwoBinding

class TwoActivity : BaseVBActivity<ActivityTwoBinding>() {
    override fun initUI() {
        super.initUI()
        getBinding().back.singleClick { finish() }
    }
}