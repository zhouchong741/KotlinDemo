package com.kotlin.demo.network

import com.kotlin.demo.dao.MainPageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:35
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MainPageRepository private constructor(
    private val mainPageDao: MainPageDao,
    private val gankNetWork: GankNetWork
) {

    suspend fun refreshBanner(url: String) = requestBanner(url)
    suspend fun refreshGanHuo(url: String) = requestGanHuo(url)
    suspend fun refreshMeiZi(url: String) = requestMeiZi(url)

    private suspend fun requestBanner(url: String) = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchBanner(url)
        mainPageDao.cacheBanner(response)
        response
    }

    private suspend fun requestGanHuo(url: String) = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchGanHuo(url)
        mainPageDao.cacheGanHuo(response)
        response
    }

    private suspend fun requestMeiZi(url: String) = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchMeiZi(url)
        mainPageDao.cacheMeiZi(response)
        response
    }

    companion object {
        private var repository: MainPageRepository? = null
        fun getInstance(dao: MainPageDao, netWork: GankNetWork): MainPageRepository {
            if (repository == null) {
                synchronized(MainPageRepository::class.java) {
                    if (repository == null) {
                        repository = MainPageRepository(dao, netWork)
                    }
                }
            }
            return repository!!
        }
    }
}