package com.kotlin.demo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.R
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.util.GlideUtils.load

/**
 * @author: zhouchong
 * 创建日期: 2020/11/3-14:48
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class PictureAdapter(private var dataList: List<String>, private val context: Context) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(R.layout.item_picture.inflate(parent))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.ivPicture.load(dataList[position])
        holder.tvPosition.text = "${position + 1} / ${dataList.size}"
    }

    override fun getItemCount(): Int = dataList.size

    inner class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPicture: ImageView = view.findViewById(R.id.ivPicture)
        val tvPosition: TextView = view.findViewById(R.id.tvPosition)
    }
}