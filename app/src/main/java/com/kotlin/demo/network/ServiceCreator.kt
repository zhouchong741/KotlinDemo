package com.kotlin.demo.network

import com.google.gson.GsonBuilder
import com.kotlin.demo.callback.GsonTypeAdapterFactory
import com.kotlin.demo.extension.logD
import com.kotlin.demo.extension.logJson
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.StringUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    private val TAG: String = this.javaClass.simpleName

    const val BASE_URL = "https://gank.io"

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
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


    private fun getHttpClient(): OkHttpClient {
        val level = HttpLoggingInterceptor.Level.BODY
        // 打印日志
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            if (message!!.contains("OK")) {
                logJson(CommonUtils.appName, StringUtils.cutStartWith(message), TAG)
            }

            if (message.contains("data")) {
                logJson(CommonUtils.appName, message, TAG)
            }
        }
        loggingInterceptor.level = level

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }

}