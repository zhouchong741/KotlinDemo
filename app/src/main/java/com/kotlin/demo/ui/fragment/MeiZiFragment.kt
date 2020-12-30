package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.MeiZiAdapter
import com.kotlin.demo.base.BaseViewBindingFragment
import com.kotlin.demo.databinding.FragmentMeiziBinding
import com.kotlin.demo.gank.MeiZiViewModel
import com.kotlin.demo.ui.activity.picture.PictureActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MeiZiFragment : BaseViewBindingFragment() {

    private lateinit var viewBinding: FragmentMeiziBinding
    private lateinit var adapter: MeiZiAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getMeiZiFactory()).get(MeiZiViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = FragmentMeiziBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        val gridLayoutManager = GridLayoutManager(activity, 2)
        viewBinding.rvMeiZi.layoutManager = gridLayoutManager

        adapter = MeiZiAdapter(viewModel.dataList, activity)
        viewBinding.rvMeiZi.adapter = adapter

        adapter.setICallback(object : MeiZiAdapter.ICallback {
            override fun onClick(view: View, imgUrl: String) {
                PictureActivity.startActivity(
                    activity,
                    CommonUtils.makeSceneTransitionAnimation(activity, view, "meizi"),
                    imgUrl
                )
            }
        })
    }

    private fun initView() {
        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewBinding.refreshLayout.setOnLoadMoreListener {
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
                        it
                    )
                }
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }

            loadFinished()

            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                //上拉加载数据时，返回数据条目为0时处理。
                viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                //上拉加载数据时，返回数据条目为0时处理。
                viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (viewBinding.refreshLayout.state) {
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
                viewBinding.refreshLayout.closeHeaderOrFooter()
            } else {
                viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
            }
        })
    }
}