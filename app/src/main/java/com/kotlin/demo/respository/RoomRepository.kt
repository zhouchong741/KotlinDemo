package com.kotlin.demo.respository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import androidx.lifecycle.LiveData
import com.kotlin.demo.dao.MainPageDao
import com.kotlin.demo.dao.UserDao
import com.kotlin.demo.model.User
import com.kotlin.demo.network.GankNetWork
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.respository.IDataStoreRepository
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
class RoomRepository(val context: Context)  {

    companion object {
        private var repository: RoomRepository? = null
        fun getInstance(context: Context): RoomRepository {
            if (repository == null) {
                synchronized(RoomRepository::class.java) {
                    if (repository == null) {
                        repository = RoomRepository(context)
                    }
                }
            }
            return repository!!
        }
    }
}