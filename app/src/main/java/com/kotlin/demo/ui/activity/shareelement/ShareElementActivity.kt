package com.kotlin.demo.ui.activity.shareelement

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.ActivityOptionsCompat
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.util.StatusBarUtils
import kotlinx.android.synthetic.main.activity_share_element.*

class ShareElementActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_share_element
    }

    override fun initView() {
        setStatusBar()
        ivShareElementLogo.setOnClickListener {
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