package com.kotlin.demo.util

import android.annotation.SuppressLint
import com.kotlin.demo.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 10:01
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object TimeUtils {
    /**
     * 获取年份
     */
    fun getYear(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        return String.format("%s", year)
    }

    /**
     * 获取时间 格式 12:00:00
     */
    fun getHHmmss(): String {
        val calendar = Calendar.getInstance()
        val hh = calendar.get(Calendar.HOUR_OF_DAY)
        val mm = calendar.get(Calendar.MINUTE)
        val ss = calendar.get(Calendar.SECOND)

        if (mm < 10 || ss < 10) {
            var time = "$hh:"
            time = if (mm < 10) {
                time + "0$mm:"
            } else {
                "$time$mm:"
            }

            time = if (ss < 10) {
                time + "0$ss"
            } else {
                "$time$ss"
            }
            return time
        }
        return String.format("%s:%s:%s", hh, mm, ss)
    }

    /**
     * 获取年月日 格式 2019-6-19
     * TimeUtils.getYMD()
     */
    fun getYMD(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%s-%s-%s", year, month, day)
    }

    /**
     * 获取年月日 格式 2020-09-17
     * TimeUtils.getYMD2()
     */
    @SuppressLint("SimpleDateFormat")
    fun getYMD2(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat(CommonUtils.getString(R.string.str_time_format))
        return simpleDateFormat.format(date)
    }

    /**
     * 获取年月日 格式 2020-09-17 12:00:00
     * TimeUtils.getYMDHMS()
     */
    @SuppressLint("SimpleDateFormat")
    fun getYMDHMS(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(date)
    }

}