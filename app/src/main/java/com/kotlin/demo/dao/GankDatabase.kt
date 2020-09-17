package com.kotlin.demo.dao

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 10:07
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object GankDatabase {
    private var mainPageDao: MainPageDao? = null

    fun getMainPage(): MainPageDao {
        if (mainPageDao == null) {
            mainPageDao = MainPageDao()
        }
        return mainPageDao!!
    }
}