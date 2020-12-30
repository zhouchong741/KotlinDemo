package com.kotlin.demo.ui.activity.shareelement

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityShareElementBinding
import com.kotlin.demo.util.StatusBarUtils

/**
 * @author zhouchong
 * 创建日期: 2020/12/4 15:58
 * 描述：共享元素
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ShareElementActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityShareElementBinding
    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityShareElementBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        setStatusBar()
        viewBinding.ivShareElementLogo.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    }

    companion object {
        fun startActivity(context: Context, options: ActivityOptionsCompat) {
            val intent: Intent by lazy {
                Intent(context, ShareElementActivity::class.java)
            }
            context.startActivity(intent, options.toBundle())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAfterTransition()
    }
}