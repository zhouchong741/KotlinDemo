package com.kotlin.demo.gank

import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.preferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlin.demo.respository.IDataStoreRepository
import kotlinx.coroutines.launch

/**
 * @author: zhouchong
 * 创建日期: 2020/10/15-20:14
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DataStoreViewModel @ViewModelInject constructor(
    private val dataStoreRepository: IDataStoreRepository
) :
    ViewModel() {

    /**
     * 保存 DataStore
     */
    fun saveDataByDataStore(key: Preferences.Key<Boolean>) {
        viewModelScope.launch {
            dataStoreRepository.saveData(key)
        }
    }

    /**
     * 获取 DataStore
     */
    fun getDataByDataStore(key: Preferences.Key<Boolean>) =
        dataStoreRepository.readData(key).asLiveData()

    object PreferencesKeys {
        // DataStore 的测试的 key
        val KEY_GITHUB = preferencesKey<Boolean>("GitHub")
    }
}
