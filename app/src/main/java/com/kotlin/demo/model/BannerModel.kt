package com.kotlin.demo.model

import com.google.gson.annotations.SerializedName

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:11
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
data class BannerModel(@SerializedName("data") val itemList: List<Item>, val status: String) :
    BaseModel() {

    data class Item(
        val image: String,
        val title: String,
        val url: String
    )
}