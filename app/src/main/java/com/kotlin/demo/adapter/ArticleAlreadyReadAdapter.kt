package com.kotlin.demo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.R
import com.kotlin.demo.util.GlideUtils.load
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.ui.activity.WebViewActivity

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 15:49
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class ArticleAlreadyReadAdapter(
    private var dataList: List<GanHuoModel.Item>,
    private val articleActivity: ArticleActivity,
) :
    RecyclerView.Adapter<ArticleAlreadyReadAdapter.ViewHolder>() {

    interface ICallback {
        fun onClick(view: View, data: GanHuoModel.Item)
    }

    private lateinit var callback: ICallback

    fun setICallback(callback: ICallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_ganhuo.inflate(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = dataList[position].title
        holder.tvType.text = dataList[position].type

        if (dataList[position].images.isNotEmpty()) {
            holder.ivImg.load(dataList[position].images[0])
        }

        holder.itemView.setOnClickListener {
            callback.onClick(holder.itemView, dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val ivImg: ImageView = view.findViewById(R.id.ivImg)
    }

}