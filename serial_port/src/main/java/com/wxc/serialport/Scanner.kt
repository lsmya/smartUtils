package com.wxc.serialport

import android.view.KeyEvent
import android.view.View
import android.widget.EditText


/**
 * 扫码枪：相当于软键盘使用的
 * 1、一定需要一个EditText（充当扫码枪输入的容器）
 * 2、有弹出软键盘的，扫码内容可能会顺序不对和乱码，需要关闭软键盘，内容正确
 * 3、扫码出来后，一般扫码枪是有标识符结束的，一般是键盘上的
 * keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN
 * 返回当前扫码内容
 * 4、最后会有KeyEvent.KEYCODE_DPAD_DOWN，到其他view上去
 * 5、还没能解决如何设置timeout问题，让接口提示扫码失败
 */
object Scanner {
    /**
     * 显示的/隐藏的 EditText 获得光标，准备扫码
     * @param callback 返回扫码结果数据，即EditText内的内容
     */
    fun setScanResultListener(editText: EditText, callback: (String) -> Unit) {
        //获得光标
        editText.setFocusable(true)
        editText.setFocusableInTouchMode(false)
        editText.requestFocus()
        //增加软键盘监听，扫出来内容会自己填充到editText中去的
        editText.setOnKeyListener(object : View.OnKeyListener {
            //on scan finish , last 3 KeyEvent log
            //keycode=66,event.getAction()=0  -> 66=KeyEvent.KEYCODE_ENTER=换行 , 1=KeyEvent.ACTION_DOWN=按下
            //keycode=66,event.getAction()=1  -> 66=KeyEvent.KEYCODE_ENTER=换行 , 1=KeyEvent.ACTION_UP=抬起
            //keycode=20,event.getAction()=0  -> 20=KeyEvent.KEYCODE_DPAD_DOWN=按键按下 , 1=KeyEvent.ACTION_DOWN=按下
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                //LogUtil.i("keycode="+keyCode+",event.getAction()="+event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    callback.invoke(editText.text.toString())//返回结果值，看需要使用了
                    return true
                }
                return false
            }
        })
    }

}
