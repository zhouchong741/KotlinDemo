package com.kotlin.demo.network

import com.google.gson.GsonBuilder
import com.kotlin.demo.callback.GsonTypeAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:43
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object ServiceCreator {
    const val BASE_URL = "https://gank.io"

    private val httpClient = OkHttpClient.Builder().build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapterFactory(
                    GsonTypeAdapterFactory()
                ).create()
            )
        )

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}