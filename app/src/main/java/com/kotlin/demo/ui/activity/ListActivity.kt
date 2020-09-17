package com.kotlin.demo.ui.activity

import android.os.Bundle
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
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.TimeUtils
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity() {

    private val TAG: String = this.javaClass.simpleName

    private lateinit var adapter: BannerAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getBannerFactory()).get(
            BannerViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val linerLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linerLayoutManager
        linerLayoutManager.orientation = RecyclerView.HORIZONTAL
        adapter = BannerAdapter(viewModel.dataList, this)
        recyclerView.adapter = adapter
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

        // btn
        btnGanHuo.setOnClickListener {
            GanHuoActivity.startActivity(this)
        }

        btnMeizi.setOnClickListener {
            MeiZiActivity.startActivity(this)
        }

        btnVerification.setOnClickListener {
            VerificationActivity.startActivity(this)
        }

        // for test
        var time = TimeUtils.getHHmmss()
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
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(this, it)
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