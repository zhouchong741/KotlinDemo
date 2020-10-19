package com.kotlin.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.demo.dao.UserDao
import com.kotlin.demo.model.User

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-13:03
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, UserDatabase::class.java, "UserDataBase").allowMainThreadQueries().build()
            }
            return instance as UserDatabase
        }
    }
}