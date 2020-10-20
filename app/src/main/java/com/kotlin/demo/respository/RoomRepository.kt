package com.kotlin.demo.respository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.dao.MainPageDao
import com.kotlin.demo.dao.UserDao
import com.kotlin.demo.database.UserDatabase
import com.kotlin.demo.model.User
import com.kotlin.demo.network.GankNetWork
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.respository.IDataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * @author: zhouchong
 * 创建日期: 2020/10/16-8:24
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class RoomRepository(val context: Context) {

    private val userDao by lazy { UserDatabase.getInstance(context).userDao() }

    fun insert(user: User) = userDao.insertAll(user)

    fun getAllUser(): LiveData<List<User>> = userDao.getAll()

    fun getUser(userName: String): LiveData<User> = userDao.getUser(userName)

    // 只执行一次的使用 suspend
    fun queryUserByIdOnce(id: Int): User = userDao.getUserByIdOnce(id)

    fun queryUserById(id:Int):LiveData<User> = userDao.getUserById(id)

    suspend fun updateUser(user: User) {
        withContext(IO) {
            userDao.update(user)
        }
    }

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