package com.kotlin.demo.manager

import android.app.Activity
import com.kotlin.demo.extension.logD

/**
 * @author: zhouchong
 * 创建日期: 2020/9/3 20:44
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object BaseAppManager {
    private val TAG: String = this.javaClass.simpleName
    private val mActivities: MutableList<Activity> = mutableListOf()
    private var instance: BaseAppManager? = null

    @Synchronized
    fun getInstance(): BaseAppManager? {
        if (instance == null) {
            synchronized(BaseAppManager::class.java) {
                if (instance == null) {
                    instance = BaseAppManager
                }
            }
        }
        return instance
    }

    @Synchronized
    fun addActivity(activity: Activity) {
        logD(TAG, activity.javaClass.simpleName)
        if (!mActivities.contains(activity)) {
            mActivities.add(activity)
        }
    }
}