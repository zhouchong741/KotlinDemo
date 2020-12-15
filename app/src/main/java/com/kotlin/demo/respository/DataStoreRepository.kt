package com.kotlin.demo.respository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * @author: zhouchong
 * 创建日期: 2020/10/16-8:24
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DataStoreRepository(val context: Context) : IDataStoreRepository {

    private val PREFERENCE_DATA_STORE = "DataStore"
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_DATA_STORE
    )

    override suspend fun saveData(key: Preferences.Key<Boolean>) {
        dataStore.edit { mutablePreferences ->
            val value = mutablePreferences[key] ?: false
            mutablePreferences[key] = !value
        }
    }

    override fun readData(key: Preferences.Key<Boolean>): Flow<Boolean> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw  it
            }
        }.map { preferences ->
            preferences[key] ?: false
        }

    companion object {
        private var repository: DataStoreRepository? = null
        fun getInstance(context: Context): DataStoreRepository {
            if (repository == null) {
                synchronized(DataStoreRepository::class.java) {
                    if (repository == null) {
                        repository = DataStoreRepository(context)
                    }
                }
            }
            return repository!!
        }
    }
}