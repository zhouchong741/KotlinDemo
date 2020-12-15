package com.kotlin.demo.respository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kotlin.demo.database.UserDatabase
import com.kotlin.demo.model.User
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

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