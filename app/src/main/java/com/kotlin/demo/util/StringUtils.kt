package com.kotlin.demo.util

/**
 * @author: zhouchong
 * 创建日期: 2020/9/18 18:29
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object StringUtils {
    /**
     * 截取指定字符串之后的文字
     */
    fun cutStartWith(text: String): String {
        val str = text.substring(0, text.indexOf("http"))
        return text.substring(str.length, text.length)
    }

    /**
     * 截取指定字符串之前的数据
     */
    fun cutFrontWith(text: String): String {
        return text.substring(0, text.indexOf("http"))
    }
}