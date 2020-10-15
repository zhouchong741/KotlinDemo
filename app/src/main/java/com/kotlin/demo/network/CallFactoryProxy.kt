package com.kotlin.demo.network

import com.kotlin.demo.extension.logD
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Request

/**
 * @author: zhouchong
 * 创建日期: 2020/10/15-10:44
 * 描述：多 baseUrl 切换
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
abstract class CallFactoryProxy(private val delegate: Call.Factory) : Call.Factory {

    private val TAG: String = this.javaClass.simpleName

    override fun newCall(request: Request): Call {
        val baseUrlName =
            request.header(NAME_BASE_URL)
        if (baseUrlName != null) {
            val newHttpUrl = getNewUrl(baseUrlName, request)
            if (newHttpUrl != null) {
                val newRequest = request.newBuilder().url(newHttpUrl).build()
                return delegate.newCall(newRequest)
            } else {
                logD(TAG, "getNewUrl() return null when baseUrlName==$baseUrlName")
            }
        }
        return delegate.newCall(request)
    }

    abstract fun getNewUrl(baseUrlName: String?, request: Request?): HttpUrl?

    companion object {
        private const val NAME_BASE_URL = "BaseUrlName"
    }
}