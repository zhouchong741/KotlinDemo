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
import com.kotlin.demo.util.GlideUtils.loadTrans

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

    interface ICallback {
        fun onClick()
    }

    private lateinit var callback: ICallback

    fun setICallback(callback: ICallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(R.layout.item_picture.inflate(parent))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.ivPicture.loadTrans(dataList[position], 4f)

        holder.ivPicture.setOnClickListener {
            callback.onClick()
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPicture: ImageView = view.findViewById(R.id.ivPicture)
    }
}