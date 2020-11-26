package com.kotlin.demo.util.helper

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * @author: zhouchong
 * 创建日期: 2020/11/26-20:14
 * 描述：深度页面效果
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DepthPageTransformer : ViewPager2.PageTransformer {
    private val minScale = 0.75f
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pageWidth = width
            when {
                position < -1 -> {
                    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> {
                    // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> {
                    // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    translationX = pageWidth * -position
                    // Move it behind the left page
                    translationZ = -1f

                    // Scale the page down (between MIN_SCALE and 1)
                    val scaleFactor = (minScale + (1 - minScale) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> {
                    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}