package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.demo.R
import com.kotlin.demo.adapter.ArticleAlreadyReadAdapter
import com.kotlin.demo.base.BaseFragment
import com.kotlin.demo.gank.GanHuoViewModel
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.ui.activity.article.ArticleActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.fragment_already_read.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 19:16
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class AlreadyReadFragment(private val articleActivity: ArticleActivity) : BaseFragment() {
    private lateinit var articleAlreadyReadAdapter: ArticleAlreadyReadAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getGanHuoFactory()).get(GanHuoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_already_read,
            container,
            false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        refreshLayout.setOnLoadMoreListener {
            viewModel.onLoad()
        }

        observe()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        articleAlreadyReadAdapter = ArticleAlreadyReadAdapter(viewModel.dataList, articleActivity)
        recyclerView.adapter = articleAlreadyReadAdapter

        articleAlreadyReadAdapter.setICallback(object : ArticleAlreadyReadAdapter.ICallback {
            override fun onClick(view: View, data: GanHuoModel.Item) {
                WebViewActivity.startActivity(
                    activity,
                    CommonUtils.makeSceneTransitionAnimation(activity, view, "article"),
                    data.url,
                    data.title,
                    data.images[0]
                )
            }
        })
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
                        activity,
                        it)
                }
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                // 首次进入页面时，获取数据条目为0时处理。
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                // 上拉加载数据时，返回数据条目为0时处理。
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }

            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    articleAlreadyReadAdapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    articleAlreadyReadAdapter.notifyItemRangeChanged(itemCount, response.itemList.size)
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