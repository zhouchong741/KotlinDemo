package com.kotlin.demo.entity

import android.content.Context
import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @author: zhouchong
 * 创建日期: 2020/9/18 8:51
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class TabEntity : CustomTabEntity {

    private var mTitle: String? = null
    private var mSelectedIcon: Int = 0
    private var mUnSelectedIcon: Int = 0

    constructor(title: String, mUnSelectedIcon: Int) : super() {
        mTitle = title
        this.mUnSelectedIcon = mUnSelectedIcon
    }

    constructor(context: Context, title: Int, mUnSelectedIcon: Int) : super() {
        mTitle = context.getString(title)
    }

    constructor(title: String, selectedIcon: Int, unSelectedIcon: Int) : super() {
        mTitle = title
        mSelectedIcon = selectedIcon
        mUnSelectedIcon = unSelectedIcon
    }

    override fun getTabTitle() = mTitle!!


    override fun getTabSelectedIcon() = mSelectedIcon


    override fun getTabUnselectedIcon() = mUnSelectedIcon


}