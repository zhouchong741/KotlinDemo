package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.GankBaseApplication
import com.kotlin.demo.R
import com.kotlin.demo.adapter.GanHuoAdapter
import com.kotlin.demo.base.BaseFragment
import com.kotlin.demo.gank.GanHuoViewModel
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.ui.activity.WebViewActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ResponseHandler
import com.kotlin.demo.util.ToastUtils
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.activity_gan_huo.*
import java.util.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class GanHuoFragment : BaseFragment() {

    private lateinit var adapter: GanHuoAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.getGanHuoFactory()).get(GanHuoViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_ganhuo
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
        val linerLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linerLayoutManager

        adapter = GanHuoAdapter(viewModel.dataList, activity)
        recyclerView.adapter = adapter

        adapter.setICallback(object : GanHuoAdapter.ICallback {
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


        // 拖动排序
        val itemTouchHelper = ItemTouchHelper(mCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val mCallBack: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int {
            // 拖动排序
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            // 左滑删除
            val swipeFlags = ItemTouchHelper.LEFT
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            Collections.swap(viewModel.dataList, viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.dataList.removeAt(viewHolder.adapterPosition)
            adapter.notifyItemRemoved(viewHolder.adapterPosition)
        }
    }

    // 插值器 滑动速度
    private val interpolator: Interpolator = Interpolator { p0 -> p0 }

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
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let {
                    if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else ToastUtils.showToast(
                        activity,
                        it
                    )
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
                    val resId = R.anim.layout_animation_from_bottom
                    val layoutAnimationController =
                        AnimationUtils.loadLayoutAnimation(activity, resId)
                    recyclerView.layoutAnimation = layoutAnimationController
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