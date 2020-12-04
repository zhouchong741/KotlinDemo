package com.kotlin.demo.ui.activity.meizi

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.MeiZiAdapter
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityMeiziBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.gank.MeiZiViewModel
import com.kotlin.demo.ui.activity.picture.PictureActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_meizi.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class MeiZiActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityMeiziBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding

    private lateinit var adapter: MeiZiAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getMeiZiFactory()).get(MeiZiViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityMeiziBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    override fun initView() {
        includeViewBinding.tvTitle.text = getString(R.string.str_meizi)
        val gridLayoutManager = GridLayoutManager(this, 2)
        viewBinding.rvMeiZi.layoutManager = gridLayoutManager
        adapter = MeiZiAdapter(viewModel.dataList, this)
        viewBinding.rvMeiZi.adapter = adapter
        adapter.setICallback(object : MeiZiAdapter.ICallback {
            override fun onClick(view: View, imgUrl: String) {
                PictureActivity.startActivity(
                    this@MeiZiActivity,
                    CommonUtils.makeSceneTransitionAnimation(this@MeiZiActivity, view, "meizi"),
                    imgUrl
                )
            }
        })

        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
        viewBinding.refreshLayout.setOnLoadMoreListener {
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
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        this,
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
            context.startActivity(Intent(context, MeiZiActivity::class.java))
        }
    }
}