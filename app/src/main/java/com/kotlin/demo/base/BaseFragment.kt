package com.kotlin.demo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import com.kotlin.demo.callback.RequestLifecycle
import com.tencent.mmkv.MMKV

/**
 * @author: zhouchong
 * 创建日期: 2020/9/4 16:54
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
abstract class BaseFragment : Fragment(), RequestLifecycle {

    /**
     * 基类里面使用
     */
    val mmkv: MMKV = MMKV.defaultMMKV()

    /**
     * 是否已经加载过数据
     */
    private var mHasLoadedData = false

    /**
     * Fragment中由于服务器或网络异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Fragment中inflate出来的布局。
     */
    protected lateinit var rootView: View

    /**
     * Fragment中显示加载等待的控件。
     */
    private var loading: LinearLayout? = null

    /**
     * 依附的Activity
     */
    lateinit var activity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 缓存当前依附的activity
        activity = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutResId(), container, false)
        initView(rootView)
        return rootView
    }

    open fun initView(rootView: View) {
        loading = rootView.findViewById(R.id.loading_layout)
    }

    abstract fun getLayoutResId(): Int

    override fun onResume() {
        super.onResume()
        // 当Fragment在屏幕上可见并且没有加载过数据时调用
        if (!mHasLoadedData) {
            loadDataFirst()
            mHasLoadedData = true
        }
    }

    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
    }

    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
    }

    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
    }

    /**
     * 页面首次可见时调用一次该方法，在这里可以请求网络数据等。
     */
    open fun loadDataFirst() {

    }
    /**
     * 当Fragment中的加载内容服务器返回失败或网络异常，通过此方法显示提示界面给用户。
     *
     * @param tip 界面中的提示信息
     * @param block 点击屏幕重新加载，回调处理。
     */
    protected fun showLoadErrorView(tip: String, block: View.() -> Unit) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        val viewStub = rootView.findViewById<ViewStub>(R.id.loadErrorView)
        if (viewStub != null) {
            loadErrorView = viewStub.inflate()
            val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
            loadErrorText?.text = tip
            val loadErrorRootView = loadErrorView?.findViewById<View>(R.id.loadErrorRootView)
            loadErrorRootView?.setOnClickListener {
                it?.block()
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    private fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }
}