package com.kotlin.demo.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.util.GlideUtils.load
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        initView()
    }

    private fun initView() {
        val imgUrl = intent.getStringExtra("IMG_URL")
        ivDetail.load(imgUrl!!)

        ivDetail.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun startActivity(context: Context, data: String) {
            val intent: Intent by lazy {
                Intent(context, ImageDetailActivity::class.java).apply {
                    putExtra("IMG_URL", data)
                }
            }
            context.startActivity(intent)
        }
    }
}