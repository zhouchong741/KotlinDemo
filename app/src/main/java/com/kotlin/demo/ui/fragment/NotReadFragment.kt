package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.R
import com.kotlin.demo.adapter.ArticleNotReadAdapter
import com.kotlin.demo.base.BaseViewBindingFragment
import com.kotlin.demo.databinding.FragmentNotReadBinding
import com.kotlin.demo.gank.MeiZiViewModel
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 19:16
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class NotReadFragment(private val articleActivity: ArticleActivity) : BaseViewBindingFragment() {

    private lateinit var viewBinding: FragmentNotReadBinding

    private lateinit var articleNotReadAdapter: ArticleNotReadAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getMeiZiFactory()).get(MeiZiViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = FragmentNotReadBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {
        val gridLayoutManager = GridLayoutManager(articleActivity, 2)
        viewBinding.recyclerView.layoutManager = gridLayoutManager

        articleNotReadAdapter = ArticleNotReadAdapter(viewModel.dataList, articleActivity)
        viewBinding.recyclerView.adapter = articleNotReadAdapter

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
        showLoadErrorView(msg ?: getString(R.string.unknown_error)) {
            startLoading()
        }
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let {
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        articleActivity,
                        it)
                }
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                // 首次进入 数据为0
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                // 上拉加载 返回数据为0
                viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (viewBinding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    articleNotReadAdapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    articleNotReadAdapter.notifyItemRangeChanged(itemCount, response.itemList.size)
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