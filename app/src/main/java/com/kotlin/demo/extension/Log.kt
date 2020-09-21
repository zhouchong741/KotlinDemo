package com.kotlin.demo.extension

import android.util.Log
import com.kotlin.demo.BuildConfig
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * 日志打印工具类
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


val LINE_SEPARATOR: String = System.getProperty("line.separator")

fun printLine(tag: String?, isTop: Boolean) {
    if (isTop) {
        Log.d(tag,
            "╔═══════════════════════════════════════════════════════════════════════════════════════")
    } else {
        Log.d(tag,
            "╚═══════════════════════════════════════════════════════════════════════════════════════")
    }
}

fun logJson(tag: String?, msg: String, headString: String) {
    var message: String
    message = try {
        when {
            msg.startsWith("{") -> {
                val jsonObject = JSONObject(msg)
                // 最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                jsonObject.toString(4)
            }
            msg.startsWith("[") -> {
                val jsonArray = JSONArray(msg)
                jsonArray.toString(4)
            }
            else -> {
                msg
            }
        }
    } catch (e: JSONException) {
        msg
    }
    printLine(getMethodName(tag!!), true)
    message = headString + LINE_SEPARATOR + message
    val lines: Array<String> = message.split(LINE_SEPARATOR).toTypedArray()
    for (line in lines) {
        Log.d(getMethodName(tag), "║ $line")
    }
    printLine(getMethodName(tag), false)
}
