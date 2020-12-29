package com.kotlin.demo.gank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.network.MainPageRepository

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 10:12
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class GanHuoViewModelFactory(private val repository: MainPageRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GanHuoViewModel(repository) as T
    }
}