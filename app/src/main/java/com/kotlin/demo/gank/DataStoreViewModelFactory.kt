package com.kotlin.demo.gank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.respository.DataStoreRepository

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 10:12
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class DataStoreViewModelFactory(private val repository: DataStoreRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DataStoreViewModel(repository) as T
    }
}