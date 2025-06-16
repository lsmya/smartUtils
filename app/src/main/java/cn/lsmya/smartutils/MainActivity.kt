package cn.lsmya.smartutils

import androidx.print.PrintHelper
import cn.lsmya.smart.ktx.loge
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding
import com.wxc.serialport.BasePrinter
import com.wxc.serialport.PrinterModel
import com.wxc.serialport.SerialPortUtils
import com.wxc.serialport.UsbPrinter


class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private var printer: BasePrinter? = null
    private val adapter by lazy { MyAdapter() }
    override fun initUI() {
        super.initUI()
        getBinding().recyclerView.adapter = adapter
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.add("")
        adapter.setOnItemClickListener { adapter, view, position ->
//            val childCount = getBinding().recyclerView.childCount
//            for (i in 0 until childCount) {
//                val child = getBinding().recyclerView.getChildAt(i)
////                loge(child.javaClass.name)
//            }

            printer?.printString("测试")
        }

        loge("是否支持打印:${PrintHelper.systemSupportsPrint()}")
//        NetworkPrinter().connect(PrinterModel(mode = PrintMode.WIFI, ip = "", port = 9100))
        val usbDeviceList = SerialPortUtils().getUsbDeviceList(this)
        for (device in usbDeviceList) {
            if (device.productName?.contains("print", true) == true) {
                val printerModel = PrinterModel.createUsbPrinter(device)
                printer = UsbPrinter(printerModel)
                printer?.connect(this)
            }
        }
    }


}