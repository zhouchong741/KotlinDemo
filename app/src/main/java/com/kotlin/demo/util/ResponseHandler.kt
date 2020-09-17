package com.kotlin.demo.util

import android.app.Application
import com.google.gson.JsonSyntaxException
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.extension.logW
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.jvm.Throws

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 10:55
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object ResponseHandler {
    private const val TAG = "ResponseHandler"
    fun getFailureTips(e: Throwable?): String {
        logW(TAG, "getFailureTips exception is ${e?.message}")
        return when (e) {
            is ConnectException -> GankBaseApplication.context.getString(R.string.network_connect_error)
            is SocketTimeoutException -> GankBaseApplication.context.getString(R.string.network_connect_timeout)
            is NoRouteToHostException -> GankBaseApplication.context.getString(R.string.no_route_to_host)
            is UnknownHostException -> GankBaseApplication.context.getString(R.string.network_error)
            is JsonSyntaxException -> GankBaseApplication.context.getString(R.string.json_data_error)
            else -> {
                GankBaseApplication.context.getString(R.string.unknown_error)
            }
        }
    }
}