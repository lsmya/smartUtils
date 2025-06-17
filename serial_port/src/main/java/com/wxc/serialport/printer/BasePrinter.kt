package com.wxc.serialport.printer

import com.wxc.serialport.PrinterModel
import com.wxc.serialport.utils.ESCUtil

abstract class BasePrinter(protected val printer: PrinterModel) {

    abstract fun connect()

    abstract fun close(printer: PrinterModel)

    abstract fun getConnectStatus(): Boolean

    abstract fun sendCmd(bytes: ByteArray): Boolean

    /**
     * 打印文字
     *
     * @param msg 打印的内容
     */
    fun printText(msg: String) = sendCmd(ESCUtil.getBytesForString(msg))

    /**
     * 换行打印文字
     *
     * @param msg 打印的内容
     */
    fun printTextNewLine(msg: String) = sendCmd(ESCUtil.getBytesForString(msg + "\n"))

    /**
     * 换行
     */
//        write(new byte[]{10});
    fun printLine() = sendCmd(ESCUtil.getBytesForString("\n"))

    /**
     * 打印空行
     *
     * @param size 几行
     */
    fun printLine(size: Int) = sendCmd(ESCUtil.getBytesForString("\n".repeat(size)))

    /**
     * 设置字体大小
     *
     * @param size 0:正常大小 1:两倍高 2:两倍宽 3:两倍大小 4:三倍高 5:三倍宽 6:三倍大 7:四倍高 8:四倍宽 9:四倍大小 10:五倍高 11:五倍宽 12:五倍大小
     */
    fun setTextSize(size: Int) = sendCmd(ESCUtil.setTextSize(size));

    /**
     * 字体加粗
     *
     * @param isBold true/false
     */
    fun bold(isBold: Boolean) = if (isBold) {
        sendCmd(ESCUtil.boldOn());
    } else {
        sendCmd(ESCUtil.boldOff());
    }

    /**
     * 打印一维条形码
     *
     * @param data 条码
     */
//        write(ESCUtil.getPrintBarCode(data, 5, 90, 5, 2));
    fun printBarCode(data: String) = sendCmd(ESCUtil.getPrintBarCode(data, 5, 90, 4, 2))

    /**
     * 打印二维码
     *
     * @param data 打印的内容
     */
    fun printQrCode(data: String) = sendCmd(ESCUtil.getPrintQrCode(data, 250));

    /**
     * 设置对齐方式
     *
     * @param position 0居左 1居中 2居右
     */
    fun setAlign(position: Int) =
        when (position) {
            0 -> sendCmd(ESCUtil.alignLeft())
            1 -> sendCmd(ESCUtil.alignCenter())
            2 -> sendCmd(ESCUtil.alignRight())
            else -> false
        }

    /**
     * 获取字符串的宽度
     *
     * @param str 取字符
     * @return 宽度
     */
    fun getStringWidth(str: String): Int {
        var width = 0;
        for (char in str.toCharArray()) {
            width = if (isChinese(char)) {
                width + 2
            } else {
                width + 1
            }
        }
        return width;
    }

    /**
     * 判断是否中文
     * GENERAL_PUNCTUATION 判断中文的“号
     * CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
     * HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
     *
     * @param c 字符
     * @return 是否中文
     */
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 设置行间距
     */
    fun lineSpace() = sendCmd(ESCUtil.lineSpace())

    /**
     * 切纸
     */
    fun cutPager() = sendCmd(ESCUtil.cutter())
}