package com.kotlin.demo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.R
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.util.GlideUtils.load

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 15:49
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MeiZiAdapter(
    private var dataList: List<MeiZiModel.Item>,
    private val context: Context
) :
    RecyclerView.Adapter<MeiZiAdapter.ViewHolder>() {

    interface ICallback {
        fun onClick(view: View, imgUrl: String)
    }

    private lateinit var callback: ICallback

    fun setICallback(callback: ICallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_meizi.inflate(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataList[position].images.isNotEmpty()) {
//            load(context, holder.ivMeiZi, dataList[position].images[0])
            holder.ivMeiZi.load(dataList[position].images[0], 4f)
        }

        holder.itemView.setOnClickListener {
            callback.onClick(holder.itemView, dataList[position].images[0])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMeiZi: ImageView = view.findViewById(R.id.ivMeiZi)
    }

}