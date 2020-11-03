package com.kotlin.demo.wigets.verification

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author: zhouchong
 * 创建日期: 2020/9/15 18:57
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class PwdEditText : AppCompatEditText {
    private lateinit var inputConnection: TInputConnection

    constructor(context: Context) : this(context, null){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    private fun init() {
        inputConnection = TInputConnection(null, true)
    }

    /**
     * 当输入法和EditText建立连接的时候会通过这个方法返回一个InputConnection。
     * 我们需要代理这个方法的父类方法生成的InputConnection并返回我们自己的代理类。
     */
    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection? {
        inputConnection.setTarget(super.onCreateInputConnection(outAttrs))
        return inputConnection
    }

    fun setBackSpaceListener(backSpaceListener: TInputConnection.BackspaceListener?) {
        inputConnection.setBackspaceListener(backSpaceListener)
    }
}