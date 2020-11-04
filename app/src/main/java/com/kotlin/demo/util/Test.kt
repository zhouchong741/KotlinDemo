package com.kotlin.demo.util

import com.kotlin.demo.extension.logW

/**
 * @author: zhouchong
 * 创建日期: 2020/11/3-18:50
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class Test {
    var arr = ArrayList<String>()
    private lateinit var arr1: ArrayList<String>

    companion object {
        const val PAGE_SIZE = 1
    }

    fun String.getLastChar(): String = this.substring(this.length - 1)

    fun print(str1: String, str2: String) {
        logW("TAG", "print: $str1 / $str2 ")
    }

    fun sw(v: Int) {
        when (v) {
            0 -> {
                logW("TAG", 0.toString())
            }
            1 -> {
                logW("TAG", 1.toString())
            }
        }
    }

    fun check(str: String?) {
        val res = str ?: "res"
    }

    fun useWith() {
        with(ArrayList<Int>()) {
            add(1)
            add(2)
            print("$this")
        }
    }

    fun testAlso() {
        val str = "A".also {
            logW("TAG", it)
        }

        str.let {
            logW("TAG", it)
        }
    }

    fun labelCycle() {
        val intArr = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        intArr.forEach here@{
            // 等于 3 返回 继续执行
            if (it == 3) return@here

            logW("TAG", it.toString())
        }
    }
}