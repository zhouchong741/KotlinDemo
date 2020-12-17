package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityImageDetailBinding
import com.kotlin.demo.util.GlideUtils.load
import com.kotlin.demo.util.GlideUtils.loadTrans
import com.kotlin.demo.util.StatusBarUtils

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:16
 * 描述： 图片查看
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ImageDetailActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityImageDetailBinding
    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityImageDetailBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        setStatusBar()
        val imgUrl = intent.getStringExtra("IMG_URL")
//        imgUrl?.let { viewBinding.ivDetail.load(it) }
        imgUrl?.let { viewBinding.ivDetail.loadTrans(it) }
        viewBinding.ivDetail.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    }

    companion object {
//        fun startActivity(context: Context, data: String) {
//            val intent: Intent by lazy {
//                Intent(context, ImageDetailActivity::class.java).apply {
//                    putExtra("IMG_URL", data)
//                }
//            }
//            context.startActivity(intent)
//        }
        // 共享元素
        fun startActivity(context: Context, options: ActivityOptionsCompat, data: String) {
            val intent: Intent by lazy {
                Intent(context, ImageDetailActivity::class.java).apply {
                    putExtra("IMG_URL", data)
                }
            }
            context.startActivity(intent, options.toBundle())
        }
    }
}