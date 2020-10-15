package com.kotlin.demo.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kotlin.demo.R
import java.time.Duration

/**
 * @author: zhouchong
 * 创建日期: 2020/9/16 15:49
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */

object ToastUtils {

    /**
     * 普通 Toast
     */
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * 可设置位置 Toast
     */
    fun showToastGravity(context: Context, text: String, gravity: Int) {
        val mToast = Toast(context)
        val view = View.inflate(context, R.layout.layout_toast_position, null)
        val textView = view.findViewById<TextView>(R.id.tv_toast_position)
        mToast.view = view
        textView.text = text
        mToast.setGravity(gravity, 0, 0)
        mToast.duration = Toast.LENGTH_SHORT
        mToast.show()
    }

    /**
     * 可设置 位置、时长 Toast
     */
    fun showCustomerToast(context: Context, text: String, gravity: Int, duration: Int) {
        val mToast = Toast(context)
        val view = View.inflate(context, R.layout.layout_toast_position, null)
        val textView = view.findViewById<TextView>(R.id.tv_toast_position)
        mToast.view = view
        textView.text = text
        mToast.setGravity(gravity, 0, 0)
        mToast.duration = duration
        mToast.show()
    }
}