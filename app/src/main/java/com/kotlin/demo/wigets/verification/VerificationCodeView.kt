package com.kotlin.demo.wigets.verification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.kotlin.demo.R
import com.kotlin.demo.extension.dp2px
import com.kotlin.demo.extension.sp2px

/**
 * @author: zhouchong
 * 创建日期: 2020/9/15 19:39
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class VerificationCodeView : RelativeLayout {
    private lateinit var containerEt: LinearLayout

    private lateinit var et: PwdEditText

    // 输入框数量
    private var mEtNumber = 0

    // 输入框的宽度
    private var mEtWidth = 0

    //输入框分割线
    private var mEtDividerDrawable: Drawable? = null

    //输入框文字颜色
    private var mEtTextColor = 0

    //输入框文字大小
    private var mEtTextSize = 0f

    //输入框获取焦点时背景
    private var mEtBackgroundDrawableFocus: Drawable? = null

    //输入框没有焦点时背景
    private var mEtBackgroundDrawableNormal: Drawable? = null

    //是否是密码模式
    private var mEtPwd = false

    //密码模式时圆的半径
    private var mEtPwdRadius = 0f

    //存储TextView的数据 数量由自定义控件的属性传入
    private lateinit var mPwdTextViews: Array<PwdTextView>
    private val myTextWatcher = MyTextWatcher()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    //初始化 布局和属性
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.layout_identifying_code, this)
        containerEt = findViewById<View>(R.id.container_et) as LinearLayout
        et = findViewById<View>(R.id.et) as PwdEditText
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView, defStyleAttr, 0)
        mEtNumber = typedArray.getInteger(R.styleable.VerificationCodeView_icv_et_number, 1)
        mEtWidth =
            typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_width, 42)
        mEtDividerDrawable =
            typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_divider_drawable)
        mEtTextSize = typedArray.getDimensionPixelSize(
            R.styleable.VerificationCodeView_icv_et_text_size,
            sp2px(16f, context).toInt()
        ).toFloat()
        mEtTextColor =
            typedArray.getColor(R.styleable.VerificationCodeView_icv_et_text_color, Color.BLACK)
        mEtBackgroundDrawableFocus =
            typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_focus)
        mEtBackgroundDrawableNormal =
            typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_normal)
        mEtPwd = typedArray.getBoolean(R.styleable.VerificationCodeView_icv_et_pwd, false)
        mEtPwdRadius =
            typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_pwd_radius, 0)
                .toFloat()
        //释放资源
        typedArray.recycle()

        // 当xml中未配置时 这里进行初始配置默认图片
        if (mEtDividerDrawable == null) {
            mEtDividerDrawable =
                ContextCompat.getDrawable(context, R.drawable.shape_divider_identifying)
        }
        if (mEtBackgroundDrawableFocus == null) {
            mEtBackgroundDrawableFocus =
                ContextCompat.getDrawable(context, R.drawable.shape_icv_et_bg_focus)
        }
        if (mEtBackgroundDrawableNormal == null) {
            mEtBackgroundDrawableNormal =
                ContextCompat.getDrawable(context, R.drawable.shape_icv_et_bg_normal)
        }
        initUI()
    }

    // 初始UI
    private fun initUI() {
        initTextViews(context, mEtNumber, mEtWidth, mEtDividerDrawable, mEtTextSize, mEtTextColor)
        initEtContainer(mPwdTextViews)
        setListener()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 设置当 高为 warpContent 模式时的默认值 为 50dp
        var mHeightMeasureSpec = heightMeasureSpec
        val heightMode = MeasureSpec.getMode(mHeightMeasureSpec)
        if (heightMode == MeasureSpec.AT_MOST) {
            mHeightMeasureSpec =
                MeasureSpec.makeMeasureSpec(dp2px(50f), MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, mHeightMeasureSpec)
    }

    //初始化TextView
    @Suppress("DEPRECATION")
    private fun initTextViews(
        context: Context,
        etNumber: Int,
        etWidth: Int,
        etDividerDrawable: Drawable?,
        etTextSize: Float,
        etTextColor: Int
    ) {
        // 设置 editText 的输入长度
        et.isCursorVisible = false //将光标隐藏
        et.filters = arrayOf<InputFilter>(LengthFilter(etNumber)) //最大输入长度
        // 设置分割线的宽度
        if (etDividerDrawable != null) {
            etDividerDrawable.setBounds(
                0,
                0,
                etDividerDrawable.minimumWidth,
                etDividerDrawable.minimumHeight
            )
            containerEt.dividerDrawable = etDividerDrawable
        }

        mPwdTextViews = Array(etNumber) { PwdTextView(context) }

        for (i in mPwdTextViews.indices) {
            val textView = PwdTextView(context)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, etTextSize)
            textView.setTextColor(etTextColor)
            textView.width = etWidth
            textView.height = etWidth
            if (i == 0) {
                textView.setBackgroundDrawable(mEtBackgroundDrawableFocus)
            } else {
                textView.setBackgroundDrawable(mEtBackgroundDrawableNormal)
            }
            textView.gravity = Gravity.CENTER
            textView.isFocusable = false
            mPwdTextViews[i] = textView
        }
    }

    //初始化存储TextView 的容器
    private fun initEtContainer(mTextViews: Array<PwdTextView>) {
        for (mTextView in mTextViews) {
            containerEt.addView(mTextView)
        }
    }

    private fun setListener() {
        // 监听输入内容
        et.addTextChangedListener(myTextWatcher)

        // 监听删除按键
        et.setOnKeyListener(OnKeyListener { _: View, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                onKeyDelete()
                return@OnKeyListener true
            }
            false
        })
    }

    // 给TextView 设置文字
    fun setText(inputContent: String) {
        for (i in mPwdTextViews.indices) {
            val tv = mPwdTextViews[i]
            if (tv.text.toString().trim() == "") {
                if (mEtPwd) {
                    tv.drawPwd(mEtPwdRadius)
                }
                tv.run {
                    setText(inputContent)
                }
                // 添加输入完成的监听
                if (inputCompleteListener != null && i == mPwdTextViews.size - 1) {
                    inputCompleteListener!!.inputComplete()
                }
                tv.background = mEtBackgroundDrawableNormal
                if (i < mEtNumber - 1) {
                    mPwdTextViews[i + 1].background = mEtBackgroundDrawableFocus
                }
                break
            }
        }
    }

    // 监听删除
    private fun onKeyDelete() {
        for (i in mPwdTextViews.indices.reversed()) {
            val tv = mPwdTextViews[i]
            if (tv.text.toString().trim() != "") {
                if (mEtPwd) {
                    tv.clearPwd()
                }
                tv.setText("")
                // 添加删除完成的监听
                if (inputCompleteListener != null && i == 0) {
                    inputCompleteListener!!.deleteContent()
                }
                // 添加删除完成监听
                tv.background = mEtBackgroundDrawableFocus
                if (i < mEtNumber - 1) {
                    mPwdTextViews[i + 1].background = mEtBackgroundDrawableNormal
                }
                break
            }
        }
    }

    /**
     * 获取输入文本
     *
     * @return string
     */
    fun getInputContent(): String? {
        val buffer = StringBuffer()
        for (tv in mPwdTextViews) {
            buffer.append(tv.text.toString().trim { it <= ' ' })
        }
        return buffer.toString()
    }

    /**
     * 删除输入内容
     */
    fun clearInputContent() {
        for (i in mPwdTextViews.indices) {
            if (i == 0) {
                mPwdTextViews[i].background = mEtBackgroundDrawableFocus
            } else {
                mPwdTextViews[i].background = mEtBackgroundDrawableNormal
            }
            if (mEtPwd) {
                mPwdTextViews[i].clearPwd()
            }
            mPwdTextViews[i].setText("")
        }
    }

    /**
     * 设置输入框个数
     *
     * @param etNumber
     */
    fun setEtNumber(etNumber: Int) {
        mEtNumber = etNumber
        et.removeTextChangedListener(myTextWatcher)
        containerEt.removeAllViews()
        initUI()
    }

    /**
     * 获取输入的位数
     *
     * @return int
     */
    fun getEtNumber(): Int {
        return mEtNumber
    }

    /**
     * 设置是否是密码模式 默认不是
     *
     * @param isPwdMode
     */
    fun setPwdMode(isPwdMode: Boolean) {
        mEtPwd = isPwdMode
    }

    /**
     * 获取输入的EditText 用于外界设置键盘弹出
     *
     * @return EditText
     */
    fun getEditText(): EditText? {
        return et
    }

    // 输入完成 和 删除成功 的监听
    private var inputCompleteListener: InputCompleteListener? = null

    fun setInputCompleteListener(inputCompleteListener: InputCompleteListener?) {
        this.inputCompleteListener = inputCompleteListener
    }

    interface InputCompleteListener {
        fun inputComplete()
        fun deleteContent()
    }

    private inner class MyTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            val inputStr = editable.toString()
            if (!TextUtils.isEmpty(inputStr)) {
                setText(inputStr)
                et.setText("")
            }
        }
    }

}