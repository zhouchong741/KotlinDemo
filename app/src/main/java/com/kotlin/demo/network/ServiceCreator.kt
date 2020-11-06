package com.kotlin.demo.network

import com.google.gson.GsonBuilder
import com.kotlin.demo.callback.GsonTypeAdapterFactory
import com.kotlin.demo.extension.logJson
import com.kotlin.demo.network.HttpUrl.GANK_URL
import com.kotlin.demo.network.HttpUrl.LOGIN_URL
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.StringUtils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
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

    private val builder = Retrofit.Builder()
        .baseUrl(GANK_URL)
        .callFactory(object : CallFactoryProxy(getHttpClient()) {
            override fun getNewUrl(baseUrlName: String?, request: Request?): HttpUrl? {
                // @Headers("BaseUrlName:login") 判断
                if (baseUrlName.equals("login")) {
                    val oldUrl = request?.url.toString()
//                    logD(TAG, oldUrl)
                    val newUrl = oldUrl.replace(GANK_URL, LOGIN_URL)
//                    logD(TAG, newUrl)
                    return newUrl.toHttpUrl()
                }
                return null
            }
        })
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
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            if (message.contains("OK")) {
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