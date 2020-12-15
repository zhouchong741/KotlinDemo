package com.kotlin.demo.gank

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlin.demo.respository.DataStoreRepository
import com.kotlin.demo.util.DataStoreUtils
import kotlinx.coroutines.launch

/**
 * @author: zhouchong
 * 创建日期: 2020/10/15-20:14
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DataStoreViewModel constructor(
    private val dataStoreRepository: DataStoreRepository,
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

        const val STRING_KEY = "STRING_KEY"
    }

    /**
     * DataUtils 保存数据
     */
    suspend fun saveStringData(str: String) {
        DataStoreUtils.putData(PreferencesKeys.STRING_KEY, str)
    }

    /**
     * DataUtils 获取数据
     */
    fun getStringData(key: String): LiveData<String> =
        DataStoreUtils.getData(key, "default").asLiveData()

}
