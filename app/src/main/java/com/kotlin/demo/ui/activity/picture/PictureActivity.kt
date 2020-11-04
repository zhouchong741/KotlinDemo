package com.kotlin.demo.ui.activity.picture

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager2.widget.ViewPager2
import com.kotlin.demo.R
import com.kotlin.demo.adapter.PictureAdapter
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.extension.invisibleAlphaAnimation
import com.kotlin.demo.extension.visibleAlphaAnimation
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.StatusBarUtils
import com.kotlin.demo.wigets.dialog.ShareBottomDialog
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : BaseActivity() {

    private lateinit var imgUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_picture)

        initView()
        setStatusBar()

        ClickUtil.setOnClickListener(ivLike, ivCollection, ivMessage, ivShare) {
            when (this) {
                ivShare -> {
                    showShare()
                }
            }
        }
    }

    private fun showShare() {
        ShareBottomDialog(this).showDialog(this, imgUrl)
    }

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        imgUrl = intent.getStringExtra("IMG_URL").toString()
        val dataList = mutableListOf<String>()
        dataList.add(imgUrl)
        dataList.add(imgUrl)
        dataList.add(imgUrl)
        val adapter = PictureAdapter(dataList, this)
        viewPager.adapter = adapter

        viewPager.offscreenPageLimit = 1

        tvPosition.text = "1 / ${dataList.size}"

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvPosition.text = "${position + 1} / ${dataList.size}"
            }
        })

        ivArrowClose.setOnClickListener {
            this.finishAfterTransition()
        }

        adapter.setICallback(object : PictureAdapter.ICallback {
            override fun onClick() {
                toggleImage()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAfterTransition()
    }

    private fun toggleImage() {
        if (ivArrowClose.visibility == ViewGroup.VISIBLE) {
            ivArrowClose.invisibleAlphaAnimation()
            ivShare.invisibleAlphaAnimation()
            ivLike.invisibleAlphaAnimation()
            ivCollection.invisibleAlphaAnimation()
            ivMessage.invisibleAlphaAnimation()
//            StatusBarUtils.setStatusBarVisibility(this, false)
        } else {
            ivArrowClose.visibleAlphaAnimation()
            ivShare.visibleAlphaAnimation()
            ivLike.visibleAlphaAnimation()
            ivCollection.visibleAlphaAnimation()
            ivMessage.visibleAlphaAnimation()
//            StatusBarUtils.setStatusBarVisibility(this, true)
        }
    }

    companion object {
        fun startActivity(context: Context, options: ActivityOptionsCompat, data: String) {
            val intent: Intent by lazy {
                Intent(context, PictureActivity::class.java).apply {
                    putExtra("IMG_URL", data)
                }
            }

            context.startActivity(intent, options.toBundle())
//            (context as Activity).overridePendingTransition(
//                R.anim.anl_push_bottom_in,
//                R.anim.anl_push_up_out
//            )
        }
    }
}