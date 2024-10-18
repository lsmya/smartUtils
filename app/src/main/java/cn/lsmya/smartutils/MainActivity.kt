package cn.lsmya.smartutils

import cn.lsmya.smart.ktx.openCameraOfVideo
import cn.lsmya.smart.ktx.singleClick
import cn.lsmya.smart.ktx.toast
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding


class MainActivity : BaseVBActivity<ActivityMainBinding>() {


    override fun initUI() {
        super.initUI()
        getBinding().btn.singleClick {
//            val intent = Intent(this, TwoActivity::class.java)
//            startActivity(intent)
            openCameraOfVideo {
                toast(it)
            }
        }
    }
}