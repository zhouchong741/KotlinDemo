package com.kotlin.demo.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 15:53
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */

/**
 * 解析xml布局
 *
 * @param parent 父布局
 * @param attachToRoot 是否依附到父布局
 */
fun Int.inflate(parent: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(parent.context).inflate(this, parent, attachToRoot)
}


