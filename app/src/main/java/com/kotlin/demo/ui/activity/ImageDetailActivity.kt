package com.kotlin.demo.ui.activity

import android.app.Activity
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
        fun startActivity(context: Activity) {
            val intent = Intent(context, ImageDetailActivity::class.java)
            context.startActivity(intent)
        }

        fun startActivity(context: Activity, data: String) {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra("IMG_URL", data)
            context.startActivity(intent)
        }
    }
}