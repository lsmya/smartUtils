package com.wxc.serialport

import android.view.KeyEvent

object KeyUtils {

    //控制按键 左右 tab 键 不切换 viewpage
    fun doNotSwitchViewPagerByKey(keyCode: Int) =
        keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_TAB

    /**
     * keyCode转换为字符
     */
    fun keyCodeToChar(code: Int, isShift: Boolean): String {
        return when (code) {
            KeyEvent.KEYCODE_SHIFT_LEFT -> ""
            KeyEvent.KEYCODE_0 -> if (isShift) ")" else "0"
            KeyEvent.KEYCODE_1 -> if (isShift) "!" else "1"
            KeyEvent.KEYCODE_2 -> if (isShift) "@" else "2"
            KeyEvent.KEYCODE_3 -> if (isShift) "#" else "3"
            KeyEvent.KEYCODE_4 -> if (isShift) "$" else "4"
            KeyEvent.KEYCODE_5 -> if (isShift) "%" else "5"
            KeyEvent.KEYCODE_6 -> if (isShift) "^" else "6"
            KeyEvent.KEYCODE_7 -> if (isShift) "&" else "7"
            KeyEvent.KEYCODE_8 -> if (isShift) "*" else "8"
            KeyEvent.KEYCODE_9 -> if (isShift) "(" else "9"
            KeyEvent.KEYCODE_A -> if (isShift) "A" else "a"
            KeyEvent.KEYCODE_B -> if (isShift) "B" else "b"
            KeyEvent.KEYCODE_C -> if (isShift) "C" else "c"
            KeyEvent.KEYCODE_D -> if (isShift) "D" else "d"
            KeyEvent.KEYCODE_E -> if (isShift) "E" else "e"
            KeyEvent.KEYCODE_F -> if (isShift) "F" else "f"
            KeyEvent.KEYCODE_G -> if (isShift) "G" else "g"
            KeyEvent.KEYCODE_H -> if (isShift) "H" else "h"
            KeyEvent.KEYCODE_I -> if (isShift) "I" else "i"
            KeyEvent.KEYCODE_J -> if (isShift) "J" else "j"
            KeyEvent.KEYCODE_K -> if (isShift) "K" else "k"
            KeyEvent.KEYCODE_L -> if (isShift) "L" else "l"
            KeyEvent.KEYCODE_M -> if (isShift) "M" else "m"
            KeyEvent.KEYCODE_N -> if (isShift) "N" else "n"
            KeyEvent.KEYCODE_O -> if (isShift) "O" else "o"
            KeyEvent.KEYCODE_P -> if (isShift) "P" else "p"
            KeyEvent.KEYCODE_Q -> if (isShift) "Q" else "q"
            KeyEvent.KEYCODE_R -> if (isShift) "R" else "r"
            KeyEvent.KEYCODE_S -> if (isShift) "S" else "s"
            KeyEvent.KEYCODE_T -> if (isShift) "T" else "t"
            KeyEvent.KEYCODE_U -> if (isShift) "U" else "u"
            KeyEvent.KEYCODE_V -> if (isShift) "V" else "v"
            KeyEvent.KEYCODE_W -> if (isShift) "W" else "w"
            KeyEvent.KEYCODE_X -> if (isShift) "X" else "x"
            KeyEvent.KEYCODE_Y -> if (isShift) "Y" else "y"
            KeyEvent.KEYCODE_Z -> if (isShift) "Z" else "z"
            else -> ""
        }
    }
}
