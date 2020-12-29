package com.kotlin.demo.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityWebViewBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:17
 * 描述： WebView 页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class WebViewActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityWebViewBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(link: String?) {
        viewBinding.webView.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            @Suppress("DEPRECATION")
            setAppCacheEnabled(true)
            useWideViewPort = true
            loadWithOverviewMode = true
            defaultTextEncodingName = "UTF-8"
            setSupportZoom(true)
        }

        viewBinding.webView.loadUrl(link!!)
    }

    override fun initView() {
        includeViewBinding.ivBack.setOnClickListener {
            finishAfterTransition()
        }
        val link = intent.getStringExtra("LINK_URL")
        val title = intent.getStringExtra("TITLE")
//        val imgUrl = intent.getStringExtra("IMG_URL")
        includeViewBinding.tvTitle.text = title
        initWebView(link)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAfterTransition()
    }

    companion object {
        fun startActivity(
            context: Context,
            optionsCompat: ActivityOptionsCompat,
            link: String,
            title: String,
            imgUrl: String
        ) {
            val intent: Intent by lazy {
                Intent(context, WebViewActivity::class.java).apply {
                    putExtra("LINK_URL", link)
                    putExtra("TITLE", title)
                    putExtra("IMG_URL", imgUrl)
                }
            }
            context.startActivity(intent, optionsCompat.toBundle())
        }
    }
}

