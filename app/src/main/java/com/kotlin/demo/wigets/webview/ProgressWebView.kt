package com.kotlin.demo.wigets.webview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 16:10
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class ProgressWebView : WebView {
    private lateinit var mProgressBar: ProgressBar

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        mProgressBar =
            ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        mProgressBar.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5, 0, 0)
        addView(mProgressBar)
        webChromeClient = MyWebChromeClient()
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100) {
                mProgressBar.visibility = GONE
            } else {
                if (mProgressBar.visibility == GONE) {
                    mProgressBar.visibility = VISIBLE
                }
                mProgressBar.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val layoutParams = mProgressBar.layoutParams
        layoutParams.width = l
        layoutParams.height = t
        mProgressBar.layoutParams = layoutParams
        super.onScrollChanged(l, t, oldl, oldt)
    }
}