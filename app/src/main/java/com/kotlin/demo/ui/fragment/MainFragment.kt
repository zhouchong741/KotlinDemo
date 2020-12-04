package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.BannerAdapter
import com.kotlin.demo.base.BaseViewBindingFragment
import com.kotlin.demo.databinding.FragmentMainBinding
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
import com.kotlin.demo.util.*
import com.scwang.smart.refresh.layout.constant.RefreshState
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
class MainFragment : BaseViewBindingFragment() {

    private lateinit var viewBinding: FragmentMainBinding
    private lateinit var adapter: BannerAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getBannerFactory()).get(
            BannerViewModel::class.java
        )
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = FragmentMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linerLayoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerView.layoutManager = linerLayoutManager
        linerLayoutManager.orientation = RecyclerView.HORIZONTAL
        adapter = BannerAdapter(viewModel.dataList, activity)
        viewBinding.recyclerView.adapter = adapter

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

        ClickUtil.setOnClickListener(
            viewBinding.btnGanHuo,
            viewBinding.btnMeizi,
            viewBinding.btnVerification,
            viewBinding.btnArticle,
            viewBinding.btnDataStore,
            viewBinding.btnRoom,
            viewBinding.btnBottomNavView
        ) {
            when (this) {
                viewBinding.btnGanHuo -> {
                    GanHuoActivity.startActivity(activity)
                }

                viewBinding.btnMeizi -> {
                    MeiZiActivity.startActivity(activity)
                }

                viewBinding.btnVerification -> {
                    VerificationActivity.startActivity(activity)
                }

                viewBinding.btnArticle -> {
                    ArticleActivity.startActivity(activity)
                }

                viewBinding.btnDataStore -> {
                    DataStoreActivity.startActivity(activity)
                }

                viewBinding.btnRoom -> {
                    RoomActivity.startActivity(activity)
                }

                viewBinding.btnBottomNavView -> {
                    BottomNavigationViewActivity.startActivity(activity)
                }
            }
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
        viewBinding.refreshLayout.autoRefresh(0)
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
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            when (viewBinding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyDataSetChanged()
                }
                else -> {
                }
            }

            viewBinding.refreshLayout.closeHeaderOrFooter()
        })
    }


}