package com.kotlin.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDataBase(context: Context): UserDatabase {
            return Room
                .databaseBuilder(context, UserDatabase::class.java, "UserDataBase")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .allowMainThreadQueries()
                .build()
        }
    }
}