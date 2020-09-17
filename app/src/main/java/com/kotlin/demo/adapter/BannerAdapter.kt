package com.kotlin.demo.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.util.GlideUtils.load
import com.kotlin.demo.extension.inflate
import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.ui.activity.ListActivity
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.ToastUtils

/**
 * @author: zhouchong
 * 创建日期: 2020/9/1 20:25
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class BannerAdapter(private var dataList: List<BannerModel.Item>, val mListActivity: ListActivity) :
    RecyclerView.Adapter<BannerAdapter.TestViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BannerAdapter.TestViewHolder {
        return TestViewHolder(R.layout.item_test.inflate(parent))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.ivBanner.load(dataList[position].image)
        holder.tvTitle.text = dataList[position].title

        holder.ivBanner.setOnClickListener {
//            ToastUtils.showToast(GankBaseApplication.context, CommonUtils.getString(R.string.not_open_yet))
            WebViewActivity.startActivity(mListActivity,
                dataList[position].url,
                dataList[position].title)
        }
    }

    inner class TestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBanner: ImageView = view.findViewById(R.id.ivBanner)
        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }


}
