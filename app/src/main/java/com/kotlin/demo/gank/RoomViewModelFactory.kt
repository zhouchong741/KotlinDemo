package com.kotlin.demo.gank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.respository.RoomRepository

/**
 * @author: zhouchong
 * 创建日期: 2020/12 10 13:40
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class RoomViewModelFactory(private val roomRepository: RoomRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomViewModel(roomRepository) as T
    }
}