package cn.lsmya.smart.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 身份证号码、银行卡号、手机号格式化
 * 精确的加减乘除运算
 *
 * @author lsm
 */
public class ArithUtils {
    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    private ArithUtils() {
    }

    /**
     * 将手机号格式化为123****4123的形式
     *
     * @param str 字符串
     * @return 已经处理完的字符串
     */
    public static String hintPhone(String str) {
        if (str.length() == 11) {
            String str1 = str.substring(0, 3);
            String str2 = str.substring(7, 11);
            return str1 + "****" + str2;
        } else {
            return str;
        }
    }

    /**
     * 将身份证号格式化为1234 **** **** 1234
     *
     * @param str 字符串
     * @return 已经处理完的字符串
     */
    public static String hintIDCard(String str) {
        if (str.length() > 9) {
            String str1 = str.substring(0, 4);
            String str2 = str.substring(str.length() - 4);
            return str1 + "\t****\t****\t" + str2;
        } else {
            return str;
        }
    }

    /**
     * 将银行卡号格式化为**** **** **** 4234的形式
     *
     * @param str 字符串
     * @return 已经处理完的字符串
     */
    public static String hintBank(String str) {
        if (str.length() > 9) {
            String substring = str.substring(str.length() - 4);
            return "****\t****\t*****\t" + substring;
        } else {
            return str;
        }
    }

    /**
     * 获取银行卡的四位尾号
     *
     * @param str 字符串
     * @return 已经处理完的字符串
     */
    public static String getBankEnding(String str) {
        if (TextUtils.isEmpty(str)) {
            return "----";
        } else if (str.length() > 4) {
            String str2 = str.substring(str.length() - 4);
            return str2;
        } else {
            return str;
        }
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or kf_zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or kf_zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
