package com.kotlin.demo.ui.activity.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.BannerAdapter
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.extension.logD
import com.kotlin.demo.gank.BannerViewModel
import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.ui.activity.VerificationActivity
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.ui.activity.ganhuo.GanHuoActivity
import com.kotlin.demo.ui.activity.meizi.MeiZiActivity
import com.kotlin.demo.util.*
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val TAG: String = this.javaClass.simpleName

    private lateinit var adapter: BannerAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getBannerFactory()).get(
            BannerViewModel::class.java
        )
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val linerLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linerLayoutManager
        linerLayoutManager.orientation = RecyclerView.HORIZONTAL
        adapter = BannerAdapter(viewModel.dataList, this)
        recyclerView.adapter = adapter

        adapter.setICallback(object : BannerAdapter.ICallback {
            override fun onClick(view: View, data: BannerModel.Item) {
                WebViewActivity.startActivity(
                    this@MainActivity,
                    CommonUtils.makeSceneTransitionAnimation(this@MainActivity, view, "article"),
                    data.url,
                    data.title,
                    data.image
                )
            }
        })

        // 画廊效果
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.findTargetSnapPosition(object : RecyclerView.LayoutManager() {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                TODO("Not yet implemented")
            }
        }, changingConfigurations, changingConfigurations)
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        // 监听
        refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        observe()

        ClickUtil.setOnClickListener(btnGanHuo, btnMeizi, btnVerification, btnArticle) {
            when (this) {
                btnGanHuo -> {
                    GanHuoActivity.startActivity(this@MainActivity)
                }

                btnMeizi -> {
                    MeiZiActivity.startActivity(this@MainActivity)
                }

                btnVerification -> {
                    VerificationActivity.startActivity(this@MainActivity)
                }

                btnArticle -> {
                    ArticleActivity.startActivity(this@MainActivity)
                }
            }
        }

        // for test
        val time = TimeUtils.getHHmmss()
        logD(TAG, time)
    }

    override fun loadDataFirst() {
        super.loadDataFirst()
        startLoading()
    }

    override fun startLoading() {
        super.startLoading()
        // 初始化请求数据
        viewModel.onRefresh()
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
        viewModel.dataListLiveData.observe(this, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let {
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        this,
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

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}