package com.kotlin.demo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.ui.activity.ImageDetailActivity
import com.kotlin.demo.ui.activity.MeiZiActivity
import com.kotlin.demo.R
import com.kotlin.demo.util.GlideUtils.load
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.util.GlideUtils

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
    private val mMeiZiActivity: MeiZiActivity,
) :
    RecyclerView.Adapter<MeiZiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_meizi.inflate(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataList[position].images.isNotEmpty()) {
            load(mMeiZiActivity, holder.ivMeiZi, dataList[position].images[0])
//            holder.ivMeiZi.load(dataList[position].images[0], 4f)
        }

        holder.itemView.setOnClickListener {
            ImageDetailActivity.startActivity(mMeiZiActivity, dataList[position].images[0])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMeiZi: ImageView = view.findViewById(R.id.ivMeiZi)
    }

}