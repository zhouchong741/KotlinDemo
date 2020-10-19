package com.kotlin.demo.gank

import androidx.lifecycle.ViewModel
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.database.UserDatabase
import com.kotlin.demo.model.User
import com.kotlin.demo.respository.RoomRepository

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-13:34
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class RoomViewModel constructor(private val roomRepository: RoomRepository) : ViewModel() {

    private val userDao = UserDatabase.getInstance(GankBaseApplication.context).userDao()

    fun insert(user: User) {
        userDao.insertAll(user)
    }

    fun query(userName: String): User {
        return userDao.getUser(userName)
    }


}