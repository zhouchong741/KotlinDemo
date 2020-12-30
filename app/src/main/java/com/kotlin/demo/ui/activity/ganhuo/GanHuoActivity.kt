package com.kotlin.demo.ui.activity.ganhuo

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.GanHuoAdapter
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityGanHuoBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.gank.GanHuoViewModel
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:19
 * 描述： 干货页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class GanHuoActivity : BaseViewBindingActivity() {
    private lateinit var viewBinding: ActivityGanHuoBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding
    private lateinit var adapter: GanHuoAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getGanHuoFactory()).get(GanHuoViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityGanHuoBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    override fun initView() {
        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
        viewBinding.refreshLayout.setOnLoadMoreListener {
            viewModel.onLoad()
        }
        observe()

        includeViewBinding.tvTitle.text = getString(R.string.str_ganhuo)
        val linerLayoutManager = LinearLayoutManager(this)
        viewBinding.recyclerView.layoutManager = linerLayoutManager
        adapter = GanHuoAdapter(viewModel.dataList, this)
        viewBinding.recyclerView.adapter = adapter
        adapter.setICallback(object : GanHuoAdapter.ICallback {
            override fun onClick(view: View, data: GanHuoModel.Item) {
                WebViewActivity.startActivity(
                    this@GanHuoActivity,
                    CommonUtils.makeSceneTransitionAnimation(this@GanHuoActivity, view, "article"),
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

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, GanHuoActivity::class.java))
        }
    }
}