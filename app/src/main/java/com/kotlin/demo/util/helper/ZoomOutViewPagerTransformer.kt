package com.kotlin.demo.util.helper

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
 * @author: zhouchong
 * 创建日期: 2020/11/5-13:36
 * 描述：ViewPager2 画廊效果 中间大 两边小
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ZoomOutViewPagerTransformer : ViewPager2.PageTransformer {
    private val minScale = 0.9f
    private val minAlpha = 0.5f

    private val defaultScale = 0.9f

    override fun transformPage(view: View, position: Float) {
        val pageWidth: Int = view.width
        val pageHeight: Int = view.height

        when {
            position < -1 -> {
                // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
                view.scaleX = defaultScale
                view.scaleY = defaultScale
            }
            position <= 1 -> { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = max(minScale, 1 - abs(position))
                val verticalMargin = pageHeight * (1 - scaleFactor) / 2
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horizontalMargin - verticalMargin / 2
                } else {
                    view.translationX = -horizontalMargin + verticalMargin / 2
                }

                // Scale the page down (between minScale and 1)
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // Fade the page relative to its size.
                view.alpha =
                    minAlpha + (scaleFactor - minScale) / (1 - minScale) * (1 - minAlpha)
            }
            else -> {
                // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
                view.scaleX = defaultScale
                view.scaleY = defaultScale
            }
        }
    }
}