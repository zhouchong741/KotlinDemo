package com.kotlin.demo.respository

import androidx.datastore.preferences.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * @author: zhouchong
 * 创建日期: 2020/10/15-20:17
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
interface IDataStoreRepository {
    suspend fun saveData(key: Preferences.Key<Boolean>)

    fun readData(key: Preferences.Key<Boolean>): Flow<Boolean>
}