package com.kotlin.demo.callback

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 14:12
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
interface RequestLifecycle {

    fun startLoading()

    fun loadFinished()

    fun loadFailed(msg: String?)
}