package com.kotlin.demo.wigets.verification

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author: zhouchong
 * 创建日期: 2020/9/15 19:18
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class PwdTextView : AppCompatEditText {
    private var radius = 0f

    private var hasPwd = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs.let {

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (hasPwd) {
            // 画一个黑色的圆
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius, paint)
        }
    }


    fun clearPwd() {
        hasPwd = false
        invalidate()
    }


    fun drawPwd(radius: Float) {
        hasPwd = true
        if (radius == 0f) {
            this.radius = width / 4.toFloat()
        } else {
            this.radius = radius
        }
        invalidate()
    }
}