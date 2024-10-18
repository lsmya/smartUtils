package cn.lsmya.smart.widget

import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import cn.lsmya.smart.R
import cn.lsmya.smart.ktx.isNotNullOrEmpty
import com.lxj.xpopup.util.XPopupUtils.dp2px
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/**
 * 手机验证码输入控件
 */
class VerificationCodeInputText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), ClipInterface {

    private var boxNumber = 4
    private var boxHeight = 150
    private var horizontalPadding = 50
    private var verticalPadding = 0

    private val TYPE_NUMBER = "number"
    private val TYPE_TEXT = "text"
    private val TYPE_PASSWORD = "password"
    private val TYPE_PHONE = "phone"

    private var boxBgFocus: Drawable? = null
    private var boxBgNormal: Drawable? = null

    private var inputType = TYPE_NUMBER

    private var mTextColor: Int = Color.BLACK

    var listener: VerCideListener? = null

    init {
        boxBgNormal = context.getDrawable(R.drawable.box_bottom_cursor_normal)
        boxBgFocus = context.getDrawable(R.drawable.box_bottom_cursor_focus)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeInputText)
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.VerificationCodeInputText_android_textColor -> {
                    mTextColor =
                        typedArray.getColor(attr, 0x000000)
                }

                R.styleable.VerificationCodeInputText_codeNumber -> {
                    boxNumber = typedArray.getInt(attr, 4)
                }

                R.styleable.VerificationCodeInputText_boxHeight -> {
                    boxHeight = typedArray.getDimensionPixelSize(
                        attr, dp2px(context, 0f)
                    )
                }

                R.styleable.VerificationCodeInputText_verticalPadding -> {
                    verticalPadding = typedArray.getDimensionPixelSize(
                        attr, dp2px(context, 0f)
                    )
                }

                R.styleable.VerificationCodeInputText_horizontalPadding -> {
                    horizontalPadding = typedArray.getDimensionPixelSize(
                        attr, dp2px(context, 0f)
                    )
                }

                R.styleable.VerificationCodeInputText_colorControlNormalDrawable -> {
                    boxBgNormal = typedArray.getDrawable(attr)
                }

                R.styleable.VerificationCodeInputText_colorControlActivatedDrawable -> {
                    boxBgFocus = typedArray.getDrawable(attr)
                }

                R.styleable.VerificationCodeInputText_boxInputType -> {
                    var index = typedArray.getInt(attr, 0)
                    inputType =
                        arrayListOf(TYPE_NUMBER, TYPE_TEXT, TYPE_PASSWORD, TYPE_PHONE)[index]
                }
            }
        }
        typedArray.recycle()
        val onKeyListener = OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                backFocusClearAll()
            }
            false
        }

        //四个输入框
        for (index in 0 until boxNumber) {
            val editText = ListenPasteEditTextTest(context)

            editText.lisenter = this
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                editText.setTextCursorDrawable(R.drawable.color_cursor)
            }
            editText.setOnKeyListener(onKeyListener)
            //设置背景颜色，就是输入框中的下划线
            setBg(editText, false)
            editText.setTextColor(mTextColor)
            editText.gravity = Gravity.CENTER
            //最多给你输入一个字符
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1))

            //设置textView输入内容的显示模式
            if (TYPE_PASSWORD == inputType) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
            } else if (TYPE_TEXT == inputType) {
                editText.inputType = InputType.TYPE_CLASS_TEXT
            } else if (TYPE_PHONE == inputType) {
                editText.inputType = InputType.TYPE_CLASS_PHONE
            } else if (TYPE_NUMBER == inputType) {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }

            editText.id = index
            //设置字符宽度
            editText.setEms(1)
            editText.doAfterTextChanged {
                if (it.isNotNullOrEmpty()) {
                    focus()
                    checkAndCommit()
                    if (index != 0) {
                        for (i in 0 until index) {
                            val editTextChild = getChildAt(i) as EditText
                            val content = editTextChild.text.toString()
                            if (content.isEmpty()) {
                                editTextChild.text = it
                                editText.text = null
                                return@doAfterTextChanged
                            }
                        }
                    }
                }
            }
            editText.setOnFocusChangeListener { view, b ->
                setBg(editText, b)
            }
            val lp = if (editText.layoutParams == null) LayoutParams(
                0,
                boxHeight
            ) else editText.layoutParams.apply {
                this.height = boxHeight
            }
            editText.layoutParams = lp
            addView(editText, index)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
        if (count > 0) {
            val child = getChildAt(0)
            val cWidth = child.measuredWidth
            val cHeight = child.measuredHeight
            val maxH = cHeight + 2 * verticalPadding
            val maxW = cWidth * count + horizontalPadding * (count + 1)
            //上面都是计算当前editText的width加上pandding，之后设置给父布局
            setMeasuredDimension(
                View.resolveSize(maxW, widthMeasureSpec),
                View.resolveSize(maxH, heightMeasureSpec)
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        val boxWidth = (measuredWidth - (horizontalPadding * (boxNumber - 1))) / boxNumber

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.visibility = View.VISIBLE

            val layoutParams = LinearLayout.LayoutParams(boxWidth, boxHeight)
            layoutParams.bottomMargin = verticalPadding
            layoutParams.topMargin = verticalPadding
            layoutParams.leftMargin = if (index == 0) 0 else horizontalPadding
            layoutParams.rightMargin = if (index == boxNumber - 1) 0 else horizontalPadding
            layoutParams.gravity = Gravity.CENTER
            child.layoutParams = layoutParams

            val cHeight = child.measuredHeight
            val cl = index * (boxWidth + horizontalPadding)
            val cr = cl + boxWidth
            val ct = verticalPadding
            val cb = ct + cHeight
            child.layout(cl, ct, cr, cb)
        }
    }

    override fun onCut() {

    }

    override fun onCopy() {

    }

    override fun onPaste() {
        val copyText = getCutAndCopyText()
        // 如果是数字并且 length 和 填写位数一致才会进行填充
        if (isNumeric(copyText) && copyText.length == boxNumber) {
            for (i in 0 until childCount) {
                (getChildAt(i) as EditText).append(copyText.get(i).toString())
            }
        }
    }

    private fun setBg(editText: EditText, focus: Boolean) {
        if (boxBgNormal != null && !focus) {
            editText.background = boxBgNormal
        } else if (boxBgFocus != null && focus) {
            editText.background = boxBgFocus
        }
    }

    private fun focus() {
        var editText: EditText
        for (i in 0 until childCount) {
            editText = getChildAt(i) as EditText
            if (editText.text.isEmpty()) {
                editText.requestFocus()
                return
            }
        }
    }

    private fun checkAndCommit() {
        val stringBuilder = StringBuilder()
        var full = false
        for (i in 0 until boxNumber) {
            val editText = getChildAt(i) as EditText
            val content = editText.text.toString()
            if (!content.isEmpty()) {
                stringBuilder.append(content)
            }
        }

        if (stringBuilder.length == boxNumber) {
            full = true
        }

        if (full) {
            if (listener != null) {
                listener?.onComplete(stringBuilder.toString())
//                backFocusClearAll()
            }
        }
    }


    //清空所有并重新输入
    fun backFocusClearAll() {
        var editText: EditText
        for (i in 0 until boxNumber) {
            editText = getChildAt(i) as EditText
            editText.setText("")
            editText.clearFocus()
        }
        getChildAt(0).requestFocus()
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return111
     */
    private fun isNumeric(str: String?): Boolean {
        if (str.isNullOrEmpty()) {
            return false
        }
        for (element in str) {
            if (!Character.isDigit(element)) {
                return false
            }
        }
        return true
    }

    /**
     * 获取剪贴板内容
     */
    private fun getCutAndCopyText(): String {
        val manager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        if (manager != null && manager.hasPrimaryClip() && manager.primaryClip!!.itemCount > 0) {
            val addedText = manager.primaryClip!!.getItemAt(0).text
            if (addedText != null) {
                return addedText.toString()
            }
        }
        return ""
    }

    fun setOnVerCideListener(listener: VerCideListener) {
        this.listener = listener

    }

    /**
     * 使输入框弹出软键盘
     * 延迟300ms，防止view没有渲染完
     */
    fun showSoftInput(position: Int = 0) {
        if (childCount > position) {
            val timer = Timer();
            timer.schedule(object : TimerTask() {
                override fun run() {
                    GlobalScope.launch(Dispatchers.Main) {
                        val imm =
                            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        val editText = getChildAt(position) as EditText
                        editText.requestFocus()
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }, 300)
        }
    }
}

interface VerCideListener {
    fun onComplete(content: String)
}