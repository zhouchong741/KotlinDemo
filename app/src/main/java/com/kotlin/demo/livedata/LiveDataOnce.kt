package com.kotlin.demo.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-20:07
 * 描述：只获取一次观察
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}