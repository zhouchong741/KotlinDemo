package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.demo.R
import com.kotlin.demo.adapter.ArticleAlreadyReadAdapter
import com.kotlin.demo.base.BaseViewBindingFragment
import com.kotlin.demo.databinding.FragmentAlreadyReadBinding
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
class AlreadyReadFragment(private val articleActivity: ArticleActivity) :
    BaseViewBindingFragment() {
    private lateinit var viewBinding: FragmentAlreadyReadBinding
    private lateinit var articleAlreadyReadAdapter: ArticleAlreadyReadAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getGanHuoFactory()).get(GanHuoViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = FragmentAlreadyReadBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewBinding.refreshLayout.setOnLoadMoreListener {
            viewModel.onLoad()
        }

        observe()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerView.layoutManager = linearLayoutManager

        articleAlreadyReadAdapter = ArticleAlreadyReadAdapter(viewModel.dataList, articleActivity)
        viewBinding.recyclerView.adapter = articleAlreadyReadAdapter

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
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                // 首次进入页面时，获取数据条目为0时处理。
                viewBinding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                // 上拉加载数据时，返回数据条目为0时处理。
                viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }

            when (viewBinding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    articleAlreadyReadAdapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    articleAlreadyReadAdapter.notifyItemRangeChanged(itemCount,
                        response.itemList.size)
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