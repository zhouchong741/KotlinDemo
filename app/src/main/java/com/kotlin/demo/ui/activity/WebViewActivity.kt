package com.kotlin.demo.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.wigets.webview.ProgressWebView
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.layout_title_bar.*

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
        fun startActivity(context: Activity, link: String, title: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("LINK_URL", link)
            intent.putExtra("TITLE", title)
            context.startActivity(intent)
        }
    }
}

