package cn.lsmya.smartutils

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.lifecycleScope
import cn.lsmya.smart.ktx.isNotNullOrEmpty
import cn.lsmya.smart.ktx.registerForMultiplePermissionsResult
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding
import com.wxc.serialport.BluetoothLeUtils
import com.wxc.serialport.printer.BasePrinter
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private var printer: BasePrinter? = null
    private val adapter by lazy { MyAdapter() }
    var thread: Executor = Executors.newSingleThreadExecutor()

    private val isMy: Boolean = true

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
            Thread {
                printer?.printTextNewLine("测试")
                printer?.printTextNewLine("测试1")
                printer?.printTextNewLine("测试2")
                printer?.printTextNewLine("测试3")
                printer?.printLine(3)
            }.start()
        }

//        Thread {
//            loge("开始连接")
//            val createNetworkPrinter = PrinterModel.createNetworkPrinter("172.16.0.180", 9100)
//            printer = NetworkPrinter(createNetworkPrinter)
//            printer?.connect()
//        }.start()


        launcher.launch(BluetoothLeUtils.getPermission())

//        val usbDeviceList = SerialPortUtils().getUsbDeviceList(this)
//        for (device in usbDeviceList) {
//            if (device.productName?.contains("print", true) == true) {
//                val printerModel = PrinterModel.createUsbPrinter(device)
//                printer = NetworkPrinter(printerModel)
//                printer?.connect()
//            }
//        }
    }


    @SuppressLint("MissingPermission")
    private val launcher = registerForMultiplePermissionsResult {
        if (it.map { it.value }.any { !it }) {
            return@registerForMultiplePermissionsResult
        }
        lifecycleScope.launch {
            BluetoothLeUtils.scanDevice(this@MainActivity).collect {
                if (it.device.name.isNotNullOrEmpty()) {
                    Log.e("MainActivity", "scan 结果: ${it.device.name} ${it.rssi}")
                }
            }
        }
    }


}