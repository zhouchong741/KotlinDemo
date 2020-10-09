package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.MeiZiAdapter
import com.kotlin.demo.base.BaseFragment
import com.kotlin.demo.gank.MeiZiViewModel
import com.kotlin.demo.ui.activity.main.Main2Activity
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_meizi.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MeiZiFragment : BaseFragment() {

    private lateinit var adapter: MeiZiAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getMeiZiFactory()).get(MeiZiViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_meizi,
            container,
            false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        val gridLayoutManager = GridLayoutManager(activity, 2)
        rvMeiZi.layoutManager = gridLayoutManager

        adapter = MeiZiAdapter(viewModel.dataList, activity)
        rvMeiZi.adapter = adapter
    }

    private fun initView() {

        refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        refreshLayout.setOnLoadMoreListener {
            viewModel.onLoad()
        }
        observe()
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

    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let {
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        activity,
                        it)
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
}