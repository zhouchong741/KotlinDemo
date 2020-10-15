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

    suspend fun refreshBanner() = requestBanner()
    suspend fun refreshGanHuo(pageNum: Int) = requestGanHuo(pageNum)
    suspend fun refreshMeiZi(url: String) = requestMeiZi(url)
    suspend fun postLogin(email: String, password: String) = requestLogin(email, password)

    private suspend fun requestBanner() = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchBanner()
        mainPageDao.cacheBanner(response)
        response
    }

    private suspend fun requestGanHuo(pageNum: Int) = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchGanHuo(pageNum)
        mainPageDao.cacheGanHuo(response)
        response
    }

    private suspend fun requestMeiZi(url: String) = withContext(Dispatchers.IO) {
        val response = gankNetWork.fetchMeiZi(url)
        mainPageDao.cacheMeiZi(response)
        response
    }

    private suspend fun requestLogin(email: String, password: String) =
        withContext(Dispatchers.IO) {
            val response = gankNetWork.postLogin(email, password)
            mainPageDao.cacheLogin()
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