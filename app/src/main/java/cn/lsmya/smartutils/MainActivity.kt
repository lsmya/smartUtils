package cn.lsmya.smartutils

import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding

class MainActivity : BaseVBActivity<ActivityMainBinding>() {
    override fun onInitUiBefore() {
        super.onInitUiBefore()
    }

    override fun initUI() {
        supportFragmentManager.beginTransaction().replace(R.id.main, MainFragment()).commit()
    }

    override fun initData() {
    }

}