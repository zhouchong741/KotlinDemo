package com.kotlin.demo.network

import com.kotlin.demo.network.api.MainPageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:39
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class GankNetWork {
    private val mainPageService = ServiceCreator.create(MainPageService::class.java)

    suspend fun fetchBanner(url: String) = mainPageService.getBanner(url).await()
    suspend fun fetchGanHuo(url: String) = mainPageService.getGanHuo(url).await()
    suspend fun fetchMeiZi(url: String) = mainPageService.getMeiZi(url).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: GankNetWork? = null

        fun getInstance(): GankNetWork {
            if (network == null) {
                synchronized(GankNetWork::class.java) {
                    if (network == null) {
                        network = GankNetWork()
                    }
                }
            }
            return network!!
        }
    }
}