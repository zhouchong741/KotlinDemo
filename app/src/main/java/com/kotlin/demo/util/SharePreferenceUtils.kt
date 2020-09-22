package com.kotlin.demo.util

import android.content.Context
import android.content.SharedPreferences
import com.kotlin.demo.GankBaseApplication

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 9:36
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object SharePreferenceUtils {
    /**
     * 获取SharedPreferences实例。
     */
    val sharedPreferences: SharedPreferences = GankBaseApplication.context.getSharedPreferences(
        CommonUtils.appPackage + "_preferences",
        Context.MODE_PRIVATE)

    fun edit(
        action: SharedPreferences.Editor.() -> Unit,
    ) {
        val editor = sharedPreferences.edit()
        action(editor)
        editor.apply()
    }
}