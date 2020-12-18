package com.kotlin.demo.wigets.loading

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import com.kotlin.demo.R
import com.kotlin.demo.extension.dp2px
import kotlin.math.cos
import kotlin.math.min

/**
 * @author: zhouchong
 * 创建日期: 2020/9/16 13:17
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class LoadingView : View {


    private val DEFAULT_EXTERNAL_ARC_COLOR = -0x1
    private val DEFAULT_INTERNAL_ARC_COLOR = -0x703b1e

    /**************
     * Default dp *
     */
    private val DEFAULT_EXTERNAL_ARC_WIDTH = 1f
    private val DEFAULT_INTERNAL_ARC_WIDTH = 0.8f

    private val DEFAULT_VIEW_PADDING_DP = 3.0f

    // 1/6
    private val DEFAULT_ARC_PADDING_SIDE_LENGTH_RATIO = 0.16666667f
    private val DEFAULT_VIEW_WRAP_CONTENT_SIDE_LENGTH = 42.0f

    /**************
     * Default px *
     */
    private var defaultViewPadding = 0f
    private var defaultWrapContentSideLength = 0f

    private var viewWidth = 0
    private var viewHeight = 0

    /**********
     * Custom *
     */
    private var easyArcPadding = 0f
    private var eastArcExternalColor = 0
    private var eastArcInternalColor = 0
    private var eastArcExternalWidth = 0f
    private var eastArcInternalWidth = 0f

    /************
     * Spinning *
     */
    private val EXTERNAL_SPIN_SPEED = 600.0f
    private val EXTERNAL_INCREASE_MAX_TIME = 100.0
    private val EXTERNAL_ANIMATION_TIME = 600.0

    private val INTERNAL_SPIN_SPEED = 200.0f
    private val INTERNAL_INCREASE_MAX_TIME = 300.0
    private val INTERNAL_ANIMATION_TIME = 600.0

    private val ARC_MAX_LENGTH = 276
    private val ARC_MIN_LENGTH = 2

    private var externalIncreaseTime = 0.0
    private var externalPastTime = 0.0
    private var externalIncrease = true
    private var externalLength = 0.0f
    private var externalProgress = 0.0f

    private var internalIncreaseTime = 0.0
    private var internalPastTime = 0.0
    private var internalIncrease = true
    private var internalLength = 0.0f
    private var internalProgress = 0.0f

    private val externalPaint = Paint()
    private val internalPaint = Paint()

    private val externalRectF = RectF()
    private val internalRectF = RectF()

    private var lastTimeAnimated: Long = 0

    constructor(context: Context) : super(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, attrs!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context!!, attrs!!)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context!!, attrs!!)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun init(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        easyArcPadding = typedArray.getDimension(
            R.styleable.LoadingView_easyArcPadding,
            easyArcPadding
        )
        eastArcExternalColor = typedArray.getColor(
            R.styleable.LoadingView_eastArcExternalColor,
            DEFAULT_EXTERNAL_ARC_COLOR
        )
        eastArcInternalColor = typedArray.getColor(
            R.styleable.LoadingView_eastArcInternalColor,
            DEFAULT_INTERNAL_ARC_COLOR
        )
        eastArcExternalWidth = typedArray.getDimension(
            R.styleable.LoadingView_eastArcExternalWidth,
            dp2px(DEFAULT_EXTERNAL_ARC_WIDTH).toFloat()
        )
        eastArcInternalWidth = typedArray.getDimension(
            R.styleable.LoadingView_eastArcInternalWidth,
            dp2px(DEFAULT_INTERNAL_ARC_WIDTH).toFloat()
        )
        externalPastTime = 0.0
        internalPastTime = 0.0
        lastTimeAnimated = SystemClock.uptimeMillis()
        defaultViewPadding = dp2px(DEFAULT_VIEW_PADDING_DP).toFloat()
        defaultWrapContentSideLength = dp2px(DEFAULT_VIEW_WRAP_CONTENT_SIDE_LENGTH).toFloat()
        externalPaint.strokeWidth = dp2px(eastArcExternalWidth).toFloat()
        externalPaint.isAntiAlias = true
        externalPaint.style = Paint.Style.STROKE
        externalPaint.color = eastArcExternalColor
        internalPaint.strokeWidth = dp2px(eastArcInternalWidth).toFloat()
        internalPaint.isAntiAlias = true
        internalPaint.style = Paint.Style.STROKE
        internalPaint.color = eastArcInternalColor
        typedArray.recycle()
        this.setupRectF()
    }

    private fun setupRectF() {
        val paddingLeft = this.paddingLeft
        val paddingTop = this.paddingTop
        val paddingRight = this.paddingRight
        val paddingBottom = this.paddingBottom
        easyArcPadding = if (easyArcPadding > 0.0f) easyArcPadding else min(
            viewWidth,
            viewHeight
        ) * DEFAULT_ARC_PADDING_SIDE_LENGTH_RATIO
        externalRectF.left = paddingLeft + defaultViewPadding
        externalRectF.top = paddingTop + defaultViewPadding
        externalRectF.right = viewWidth - paddingRight - defaultViewPadding
        externalRectF.bottom = viewHeight - paddingBottom - defaultViewPadding
        internalRectF.left = externalRectF.left + easyArcPadding
        internalRectF.top = externalRectF.top + easyArcPadding
        internalRectF.right = externalRectF.right - easyArcPadding
        internalRectF.bottom = externalRectF.bottom - easyArcPadding
    }

    override fun onDraw(canvas: Canvas?) {
        externalPaint.strokeCap = Paint.Cap.ROUND
        internalPaint.strokeCap = Paint.Cap.ROUND

        val timeInterval = SystemClock.uptimeMillis() - lastTimeAnimated
        val externalIncreaseProgress: Float =
            timeInterval * EXTERNAL_SPIN_SPEED / 1000.0f
        val internalIncreaseProgress: Float =
            timeInterval * INTERNAL_SPIN_SPEED / 1000.0f

        this.updateLength(timeInterval)

        externalProgress += externalIncreaseProgress
        if (externalProgress > 360.0f) externalProgress -= 360f

        internalProgress += internalIncreaseProgress
        if (internalProgress > 360.0f) internalProgress -= 360f

        lastTimeAnimated = SystemClock.uptimeMillis()

        val externalFrom = externalProgress - 120.0f
        val externalLength: Float =
            externalLength + ARC_MIN_LENGTH
        val internalFrom = internalProgress - 120.0f
        val internalLength: Float =
            internalLength + ARC_MIN_LENGTH

        canvas!!.drawArc(externalRectF, externalFrom, externalLength, false, externalPaint)
        canvas.drawArc(internalRectF, internalFrom, internalLength, false, internalPaint)

        this.invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val resultWidth: Float
        val resultHeight: Float

        resultWidth = when (widthMode) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> defaultWrapContentSideLength
            MeasureSpec.EXACTLY -> min(viewWidth, viewHeight).toFloat()
            else -> min(viewWidth, viewHeight).toFloat()
        }
        resultHeight = when (heightMode) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> defaultWrapContentSideLength
            MeasureSpec.EXACTLY -> min(viewWidth, viewHeight).toFloat()
            else -> min(viewWidth, viewHeight).toFloat()
        }
        setMeasuredDimension(resultWidth.toInt(), resultHeight.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        setupRectF()
        this.invalidate()
    }

    private fun updateLength(timeInterval: Long) {
        if (externalIncreaseTime >= EXTERNAL_INCREASE_MAX_TIME) {
            externalPastTime += timeInterval.toDouble()
            if (externalPastTime > EXTERNAL_ANIMATION_TIME) {
                externalPastTime -= EXTERNAL_ANIMATION_TIME
                externalIncreaseTime = 0.0
                externalIncrease = !externalIncrease
            }
            val distance = cos((externalPastTime / EXTERNAL_ANIMATION_TIME + 1) * Math.PI).toFloat() / 2 + 0.5f
            val lengthRange: Float = (ARC_MAX_LENGTH - ARC_MIN_LENGTH).toFloat()
            if (externalIncrease) {
                externalLength = distance * lengthRange
            } else {
                val decreaseLength = lengthRange * (1 - distance)
                externalProgress += externalLength - decreaseLength
                externalLength = decreaseLength
            }
        } else {
            externalIncreaseTime += timeInterval.toDouble()
        }
        if (internalIncreaseTime >= INTERNAL_INCREASE_MAX_TIME) {
            internalPastTime += timeInterval.toDouble()
            if (internalPastTime > INTERNAL_ANIMATION_TIME) {
                internalPastTime -= INTERNAL_ANIMATION_TIME
                internalIncreaseTime = 0.0
                internalIncrease = !internalIncrease
            }
            val distance = cos((internalPastTime / INTERNAL_ANIMATION_TIME + 1) * Math.PI).toFloat() / 2 + 0.5f
            val lengthRange: Float = (ARC_MAX_LENGTH - ARC_MIN_LENGTH).toFloat()
            if (internalIncrease) {
                internalLength = distance * lengthRange
            } else {
                val decreaseLength = lengthRange * (1 - distance)
                internalProgress += internalLength - decreaseLength
                internalLength = decreaseLength
            }
        } else {
            internalIncreaseTime += timeInterval.toDouble()
        }
    }

//
//    fun setEasyArcPadding(easyArcPadding: Float) {
//        this.easyArcPadding = easyArcPadding
//        setupRectF()
//    }
//
//
//    fun setEastArcExternalColor(eastArcExternalColor: Int) {
//        this.eastArcExternalColor = eastArcExternalColor
//        externalPaint.color = this.eastArcExternalColor
//    }
//
//
//    fun setEastArcInternalColor(eastArcInternalColor: Int) {
//        this.eastArcInternalColor = eastArcInternalColor
//        internalPaint.color = this.eastArcInternalColor
//    }
//
//
//    fun setEastArcExternalWidth(eastArcExternalWidth: Float) {
//        this.eastArcExternalWidth = dp2px(eastArcExternalWidth)
//        externalPaint.strokeWidth = this.eastArcExternalWidth
//    }
//
//
//    fun setEastArcInternalWidth(eastArcInternalWidth: Float) {
//        this.eastArcInternalWidth = dp2px(eastArcInternalWidth)
//        internalPaint.strokeWidth = this.eastArcInternalWidth
//    }
}