package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.MeiZiAdapter
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.gank.MeiZiViewModel
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_meizi.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class MeiZiActivity : BaseActivity() {
    private lateinit var adapter: MeiZiAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getMeiZiFactory()).get(MeiZiViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meizi)

        initView()

        val gridLayoutManager = GridLayoutManager(this, 2)
        rvMeiZi.layoutManager = gridLayoutManager

        adapter = MeiZiAdapter(viewModel.dataList, this)
        rvMeiZi.adapter = adapter
    }

    private fun initView() {
        tvTitle.text = getString(R.string.str_meizi)
        refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        refreshLayout.setOnLoadMoreListener {
            viewModel.onLoad()
        }
        onObserve()
    }

    override fun loadDataFirst() {
        super.loadDataFirst()
        startLoading()
    }

    override fun startLoading() {
        super.startLoading()
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

    private fun onObserve() {
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

            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                //上拉加载数据时，返回数据条目为0时处理。
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                //上拉加载数据时，返回数据条目为0时处理。
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyItemRangeInserted(itemCount, response.itemList.size)
                }
                else -> {
                }
            }

            if (response.page < response.page_count) {
                refreshLayout.closeHeaderOrFooter()
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData()
            }
        })
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MeiZiActivity::class.java))
        }
    }
}