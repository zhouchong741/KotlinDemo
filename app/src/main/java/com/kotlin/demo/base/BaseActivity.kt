package com.kotlin.demo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.demo.R
import com.kotlin.demo.callback.RequestLifecycle
import com.kotlin.demo.manager.BaseAppManager

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 13:28
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
open class BaseActivity : AppCompatActivity(), RequestLifecycle {

    /**
     * 依附的Activity
     */
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this

        BaseAppManager.getInstance()?.addActivity(activity)

        loadDataFirst()

        // 设置状态栏透明
//        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        // 设置状态栏
//        StatusBarUtils.setStatusBarLightMode(this, true)
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * 页面首次可见时调用一次该方法，在这里可以请求网络数据等。
     */
    open fun loadDataFirst() {

    }

    /**
     * 遮罩
     */
    private var loading: LinearLayout? = null

    /**
     * loading 文字
     */
    private var loadingText: TextView? = null

    /**
     * 开始加载，将加载等待控件显示。
     */
    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
    }

    @CallSuper
    fun startLoading(text: String) {
        loadingText?.text = text
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
    }

    /**
     * 加载完成，将加载等待控件隐藏。
     */
    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupViews()
    }

    /**
     * 获取 loading
     */
    protected open fun setupViews() {
        loading = findViewById(R.id.loading_layout)
        loadingText = findViewById(R.id.loading_msg)
        // 使用根错误布局即可
        rootView = findViewById(R.id.loadErrorView)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val ivRight = findViewById<ImageView>(R.id.ivRight)

        ivBack?.setOnClickListener { finish() }
    }

    /**
     * 加载失败，将加载等待控件隐藏。
     */
    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
    }

    /**
     * 将load error view进行隐藏。
     */
    private fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }

    /**
     * 加载失败显示的布局
     */
    private var loadErrorView: View? = null

    /**
     * inflate出来的布局
     */
    private var rootView: View? = null

    /**
     * 当加载内容服务器返回失败或网络异常，通过此方法显示提示界面给用户。
     *
     * @param tip 界面中的提示信息
     * @param block 点击屏幕重新加载，回调处理。
     */
    protected fun showLoadErrorView(tip: String, block: View.() -> Unit) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        if (rootView != null) {
            val viewStub = rootView?.findViewById<ViewStub>(R.id.loadErrorView)
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
    }

    /**
     * 点击空白区域隐藏键盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isShouldHideInput(view, ev)) {
                val inputManager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    private fun isShouldHideInput(view: View?, ev: MotionEvent): Boolean {
        if (view != null && view is EditText) {
            val leftTop = intArrayOf(0, 0)
            view.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + view.getHeight()
            val right = left + view.getWidth()
            return if (ev.x > left && ev.x < right && ev.y > top && ev.y < bottom) {
                false
            } else {
                view.setFocusable(false)
                view.setFocusableInTouchMode(true)
                true
            }
        }
        return false
    }
}
