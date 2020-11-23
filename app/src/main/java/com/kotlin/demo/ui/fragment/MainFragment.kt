package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.BannerAdapter
import com.kotlin.demo.base.BaseFragment
import com.kotlin.demo.gank.BannerViewModel
import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.ui.activity.VerificationActivity
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.ui.activity.btmnavview.BottomNavigationViewActivity
import com.kotlin.demo.ui.activity.datastore.DataStoreActivity
import com.kotlin.demo.ui.activity.ganhuo.GanHuoActivity
import com.kotlin.demo.ui.activity.meizi.MeiZiActivity
import com.kotlin.demo.ui.activity.room.RoomActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_main.btnArticle
import kotlinx.android.synthetic.main.activity_main.btnGanHuo
import kotlinx.android.synthetic.main.activity_main.btnMeizi
import kotlinx.android.synthetic.main.activity_main.btnVerification
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.refreshLayout
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MainFragment : BaseFragment() {

    private lateinit var adapter: BannerAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getBannerFactory()).get(
            BannerViewModel::class.java
        )
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linerLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linerLayoutManager
        linerLayoutManager.orientation = RecyclerView.HORIZONTAL
        adapter = BannerAdapter(viewModel.dataList, activity)
        recyclerView.adapter = adapter

        adapter.setICallback(object : BannerAdapter.ICallback {
            override fun onClick(view: View, data: BannerModel.Item) {
                WebViewActivity.startActivity(
                    activity,
                    CommonUtils.makeSceneTransitionAnimation(activity, view, "article"),
                    data.url,
                    data.title,
                    data.image
                )
            }
        })
        // 画廊效果
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        // 监听
        refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        observe()

        // btn
        btnGanHuo.setOnClickListener {
            GanHuoActivity.startActivity(activity)
        }

        btnMeizi.setOnClickListener {
            MeiZiActivity.startActivity(activity)
        }

        btnVerification.setOnClickListener {
            VerificationActivity.startActivity(activity)
        }

        btnArticle.setOnClickListener {
            ArticleActivity.startActivity(activity)
        }

        btnDataStore.setOnClickListener {
            DataStoreActivity.startActivity(activity)
        }

        btnRoom.setOnClickListener {
            RoomActivity.startActivity(activity)
        }

        btnBottomNavView.setOnClickListener {
            BottomNavigationViewActivity.startActivity(activity)
        }
    }

    override fun loadDataFirst() {
        super.loadDataFirst()
        startLoading()
    }

    override fun startLoading() {
        super.startLoading()
//        viewModel.onRefresh()
        // 有下拉加载的动画
        refreshLayout.autoRefresh(0)
    }

    override fun loadFailed(msg: String?) {
        super.loadFailed(msg)
        showLoadErrorView(
            msg ?: GankBaseApplication.context.getString(
                R.string.unknown_error
            )
        ) {
            startLoading()
        }
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let {
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        activity,
                        it
                    )
                }
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyDataSetChanged()
                }
                else -> {
                }
            }

            refreshLayout.closeHeaderOrFooter()

        })
    }


}