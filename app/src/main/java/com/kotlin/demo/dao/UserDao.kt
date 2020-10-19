package com.kotlin.demo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userName = :name")
    fun getUser(name: String): User

    @Insert
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)
}