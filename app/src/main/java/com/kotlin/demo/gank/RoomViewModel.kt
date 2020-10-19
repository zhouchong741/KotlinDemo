package com.kotlin.demo.gank

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.demo.model.User
import com.kotlin.demo.respository.RoomRepository
import kotlinx.coroutines.launch

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-13:34
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class RoomViewModel constructor(private val roomRepository: RoomRepository) : ViewModel() {

    /**
     * 查询当前 id user LiveData 方法
     */
    fun getUserById(id: Int) = roomRepository.queryUserById(id)

    /**
     * suspend 方法 获取
     */
    fun getUserByIdOnce(id: Int): User = roomRepository.queryUserByIdOnce(id)

    /**
     * 查询全部
     */
    val getAllUser: LiveData<List<User>> = roomRepository.getAllUser()

    /**
     * 插入一条数据
     */
    fun insert(user: User) = roomRepository.insert(user)

    /**
     * 查询单条数据
     */
    fun getUser(userNam: String): LiveData<User> = roomRepository.getUser(userNam)

    /**
     * 更新数据
     */
    internal fun updateUser(user: User) {
        viewModelScope.launch {
            roomRepository.updateUser(user)
        }
    }
}