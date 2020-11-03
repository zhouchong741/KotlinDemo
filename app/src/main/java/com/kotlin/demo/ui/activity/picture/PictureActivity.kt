package com.kotlin.demo.ui.activity.picture

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.kotlin.demo.R
import com.kotlin.demo.adapter.PictureAdapter
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.util.StatusBarUtils
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        initView()
        setStatusBar()
    }

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        StatusBarUtils.setStatusBarVisibility(this, false)
    }

    private fun initView() {
        val imgUrl = intent.getStringExtra("IMG_URL")
        val dataList = mutableListOf<String>()
        dataList.add(imgUrl.toString())
        dataList.add(imgUrl.toString())
        dataList.add(imgUrl.toString())
        val adapter = PictureAdapter(dataList, this)
        viewPager.adapter = adapter

    }

    companion object {
        fun startActivity(context: Context, data: String) {
            val intent: Intent by lazy {
                Intent(context, PictureActivity::class.java).apply {
                    putExtra("IMG_URL", data)
                }
            }
            context.startActivity(intent)
        }
    }
}