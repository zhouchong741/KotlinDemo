package com.kotlin.demo.util

import android.content.Context
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.dao.GankDatabase
import com.kotlin.demo.gank.*
import com.kotlin.demo.network.GankNetWork
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.respository.DataStoreRepository

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:33
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object InjectUtil {
    private fun getMainPageRepository() =
        MainPageRepository.getInstance(GankDatabase.getMainPage(), GankNetWork.getInstance())

    fun getBannerFactory() = BannerViewModelFactory(getMainPageRepository())
    fun getGanHuoFactory() = GanHuoViewModelFactory(getMainPageRepository())
    fun getMeiZiFactory() = MeiZiViewModelFactory(getMainPageRepository())
    fun postLoginFactory() = LoginViewModelFactory(getMainPageRepository())

    private fun getDataStoreRepository(context: Context) = DataStoreRepository.getInstance(context)
    fun getDataStoreFactory() =
        DataStoreViewModelFactory(getDataStoreRepository(GankBaseApplication.context))
}