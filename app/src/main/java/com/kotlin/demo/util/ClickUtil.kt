package com.kotlin.demo.util

import android.view.View

/**
 * @author: zhouchong
 * 创建日期: 2020/9/4 17:07
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object ClickUtil {

    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param block 处理点击事件回调代码块
     */
    fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
        val listener = View.OnClickListener { it.block() }
        v.forEach { it?.setOnClickListener(listener) }
    }
}
