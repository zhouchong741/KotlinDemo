package com.kotlin.demo.ui.activity.picture

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kotlin.demo.R
import com.kotlin.demo.adapter.PictureAdapter
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityPictureBinding
import com.kotlin.demo.extension.invisibleAlphaAnimation
import com.kotlin.demo.extension.visibleAlphaAnimation
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.StatusBarUtils
import com.kotlin.demo.util.helper.DepthPageTransformer
import com.kotlin.demo.util.helper.ZoomOutViewPagerTransformer
import com.kotlin.demo.wigets.dialog.ShareBottomDialog
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.activity_picture.*

/**
 * @author zhouchong
 * 创建日期: 2020/11/5 19:59
 * 描述：图片查看器 画廊模式 ViewPager2
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class PictureActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityPictureBinding
    private lateinit var imgUrl: String

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityPictureBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        setStatusBar()
        ClickUtil.setOnClickListener(
            viewBinding.ivArrowClose,
            viewBinding.ivPrevious,
            viewBinding.ivNext,
            viewBinding.ivLike,
            viewBinding.ivCollection,
            viewBinding.ivMessage,
            viewBinding.ivShare
        ) {
            when (this) {
                viewBinding.ivShare -> {
                    showShare()
                }
                viewBinding.ivPrevious -> {
                    // 模拟滑动
                    viewBinding.viewPager.beginFakeDrag()
                    // 负数表示下一页，正数表示上一页
                    viewBinding.viewPager.fakeDragBy(500f)
                    // 结束滑动
                    viewBinding.viewPager.endFakeDrag()
                }
                viewBinding.ivNext -> {
                    // 模拟滑动
                    viewBinding.viewPager.beginFakeDrag()
                    // 负数表示下一页，正数表示上一页
                    viewBinding.viewPager.fakeDragBy(-500f)
                    // 结束滑动
                    viewBinding.viewPager.endFakeDrag()
                }
                viewBinding.ivArrowClose -> {
                    finishAfterTransition()
                }
            }
        }

        imgUrl = intent.getStringExtra("IMG_URL").toString()
        val dataList = mutableListOf<String>()
        dataList.add(imgUrl)
        dataList.add(imgUrl)
        dataList.add(imgUrl)
        val adapter = PictureAdapter(dataList, this)
        viewBinding.viewPager.adapter = adapter
        viewBinding.viewPager.offscreenPageLimit = 1
        viewBinding.tvPosition.text = "1 / ${dataList.size}"
        viewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewBinding.tvPosition.text = "${position + 1} / ${dataList.size}"
            }
        })
        // indicator
        viewBinding.indicator.apply {
            setSliderColor(
                CommonUtils.getColor(context, R.color.white),
                CommonUtils.getColor(context, R.color.colorAccent)
            )
            setSliderWidth(
                CommonUtils.getDimension(R.dimen.dimen_10dp),
                CommonUtils.getDimension(R.dimen.dimen_10dp)
            )
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(viewPager)
        }
        // 缩小页面 设置画廊效果
        viewBinding.viewPager.setPageTransformer(ZoomOutViewPagerTransformer())

        // 深度页面 需要设置 viewpager2 android:layout_width="match_parent"
//        viewBinding.viewPager.setPageTransformer(DepthPageTransformer())
        adapter.setICallback(object : PictureAdapter.ICallback {
            override fun onClick() {
                toggleImage()
            }
        })
        // tablayout indicator
        TabLayoutMediator(
            viewBinding.tabLayout,
            viewBinding.viewPager
        ) { tab, position -> }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAfterTransition()
    }

    private fun showShare() {
        ShareBottomDialog(this).showDialog(this, imgUrl)
    }

    private fun toggleImage() {
        if (viewBinding.ivArrowClose.visibility == ViewGroup.VISIBLE) {
            viewBinding.ivArrowClose.invisibleAlphaAnimation()
            viewBinding.ivShare.invisibleAlphaAnimation()
            viewBinding.ivLike.invisibleAlphaAnimation()
            viewBinding.ivCollection.invisibleAlphaAnimation()
            viewBinding.ivMessage.invisibleAlphaAnimation()
//            StatusBarUtils.setStatusBarVisibility(this, false)
        } else {
            viewBinding.ivArrowClose.visibleAlphaAnimation()
            viewBinding.ivShare.visibleAlphaAnimation()
            viewBinding.ivLike.visibleAlphaAnimation()
            viewBinding.ivCollection.visibleAlphaAnimation()
            viewBinding.ivMessage.visibleAlphaAnimation()
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