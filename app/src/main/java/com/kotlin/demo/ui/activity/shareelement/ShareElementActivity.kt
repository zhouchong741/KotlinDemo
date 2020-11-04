package com.kotlin.demo.ui.activity.shareelement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity

class ShareElementActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_element)
    }

    companion object {
        fun startActivity(context: Context, options: ActivityOptionsCompat) {
            val intent: Intent by lazy {
                Intent(context, ShareElementActivity::class.java)
            }
            context.startActivity(intent, options.toBundle())
        }
    }
}