package com.kotlin.demo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kotlin.demo.model.User

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-10:42
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE userName = :name")
    fun getUser(name: String): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserByIdOnce(id: Int): User

    @Insert
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)
}