package com.kotlin.demo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: zhouchong
 * 创建日期: 2020/10/19-10:40
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "age") val age: Int
)