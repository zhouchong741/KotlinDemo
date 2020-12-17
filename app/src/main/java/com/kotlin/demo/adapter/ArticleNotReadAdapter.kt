package com.kotlin.demo.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.R
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.ui.activity.ImageDetailActivity
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.GlideUtils.load

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 15:49
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class ArticleNotReadAdapter(
    private var dataList: List<MeiZiModel.Item>,
    private val articleActivity: ArticleActivity,
) :
    RecyclerView.Adapter<ArticleNotReadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_meizi.inflate(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataList[position].images.isNotEmpty()) {
            holder.ivImg.load(dataList[position].images[0], 4f)
        }

        holder.itemView.setOnClickListener {
//            val optionsCompat =
//                CommonUtils.makeSceneTransitionAnimation(articleActivity, holder.ivImg, "meizi")
//            val intent = Intent(articleActivity, ImageDetailActivity::class.java)
//            intent.putExtra("IMG_URL", dataList[position].images[0])
//            articleActivity.startActivity(intent, optionsCompat.toBundle())
            ImageDetailActivity.startActivity(articleActivity, dataList[position].images[0])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImg: ImageView = view.findViewById(R.id.ivMeiZi)
    }

}