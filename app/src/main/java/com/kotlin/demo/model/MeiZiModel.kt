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
data class MeiZiModel(
    @SerializedName("data") val itemList: List<Item>,
    val status: String,
    val page: Int,
    val page_count: Int,
    val total_counts: Int
) :
    BaseModel() {

    data class Item(
        val author: String,
        val type: String,
        val title: String,
        val views: Int,
        val desc: String,
        val category: String,
        val images: List<String>,
        val url: String
    )
}