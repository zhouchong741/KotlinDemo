package com.kotlin.demo.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 14:08
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object SnackBarUtils {
    fun show(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun show(view: View, msg: Int) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}