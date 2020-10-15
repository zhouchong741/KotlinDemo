package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:17
 * 描述： WebView 页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initView()
    }

    private fun initView() {
        val link = intent.getStringExtra("LINK_URL")
        val title = intent.getStringExtra("TITLE")
        tvTitle.text = title

        initWevView(link)
    }

    private fun initWevView(link: String?) {
        webView.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            setAppCacheEnabled(true)
            useWideViewPort = true
            loadWithOverviewMode = true
            defaultTextEncodingName = "UTF-8"
            setSupportZoom(true)
        }

        webView.loadUrl(link!!)
    }

    companion object {
        fun startActivity(context: Context, link: String, title: String) {
            val intent: Intent by lazy {
                Intent(context, WebViewActivity::class.java).apply {
                    putExtra("LINK_URL", link)
                    putExtra("TITLE", title)
                }
            }
            context.startActivity(intent)
        }
    }
}

