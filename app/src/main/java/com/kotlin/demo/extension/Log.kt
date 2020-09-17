package com.kotlin.demo.extension

import android.util.Log
import com.kotlin.demo.BuildConfig

/**
 * 日志调试工具类。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5

private val level = if (BuildConfig.DEBUG) VERBOSE else WARN

fun logV(tag: String, msg: String?) {
    if (level <= VERBOSE) {
        Log.v(tag, msg.toString())
    }
}

fun logD(tag: String, msg: String?) {
    if (level <= DEBUG) {
        Log.d(getMethodName(tag), msg.toString())
    }
}

/**
 * 获取方法名
 */
fun getMethodName(tag: String): String {
    val stackTrace = Thread.currentThread().stackTrace
    val targetElement = stackTrace[4]
//    var className: String = targetElement.className
//    val classInfo: List<String> = className.split(".")
//    if (classInfo.isNotEmpty()){
//        className =classInfo[classInfo.size -1] + ".java"
//    }
    val methodName: String = targetElement.methodName
    val lineNum: Int = targetElement.lineNumber
    return "[(${tag}:${lineNum})#${methodName})]";
}

fun logI(tag: String, msg: String?) {
    if (level <= INFO) {
        Log.i(tag, msg.toString())
    }
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Log.w(tag, msg.toString())
        } else {
            Log.w(tag, msg.toString(), tr)
        }
    }
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    if (level <= ERROR) {
        Log.e(tag, msg.toString(), tr)
    }
}

