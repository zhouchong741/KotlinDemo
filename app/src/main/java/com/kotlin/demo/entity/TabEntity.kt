package com.kotlin.demo.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @author: zhouchong
 * 创建日期: 2020/9/18 8:51
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class TabEntity(private val title: String, private val selectedIcon: Int, private val unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle() = title

    override fun getTabSelectedIcon() = selectedIcon

    override fun getTabUnselectedIcon() = unSelectedIcon
}