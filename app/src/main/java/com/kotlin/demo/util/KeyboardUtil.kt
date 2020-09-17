package com.kotlin.demo.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author: zhouchong
 * 创建日期: 2020/9/16 14:35
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */

/**
 * 隐藏软键盘
 */
object KeyboardUtil {

    fun hideKeyboard(context: Context, view: View) {
        val imm =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 先 isSoftShow 判断是否显示再用
     */
    fun toggleKeyboard(context: Context) {
        val inputMethodManager =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun isSoftShow(activity: Activity): Boolean {
        val screenHeight = activity.window.decorView.height
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return screenHeight * 2 / 3 > rect.bottom + getSoftBottomBarHeight(activity)
    }

    private fun getSoftBottomBarHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val usableHeight = displayMetrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        val realHeight = displayMetrics.heightPixels

        return if (realHeight > usableHeight) {
            realHeight - usableHeight
        } else {
            0
        }
    }
}
