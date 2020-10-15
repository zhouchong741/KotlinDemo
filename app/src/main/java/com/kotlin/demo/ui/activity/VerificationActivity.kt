package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.KeyboardUtil
import com.kotlin.demo.util.ToastUtils
import com.kotlin.demo.wigets.verification.VerificationCodeView.InputCompleteListener
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:17
 * 描述： 验证码页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class VerificationActivity : BaseActivity() {
    private var doubleDuration = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        initView()

        clearVerification.setOnClickListener {
            verificationCodeView.clearInputContent()
        }

        verificationCodeView.setInputCompleteListener(object : InputCompleteListener {
            override fun inputComplete() {
                // 隐藏键盘
                if (KeyboardUtil.isSoftShow(this@VerificationActivity)) {
                    KeyboardUtil.toggleKeyboard(this@VerificationActivity)
                }

                // 模拟请求验证
//                startLoading(getString(R.string.str_verfication_in_progress))

                ToastUtils.showToast(this@VerificationActivity,
                    "${CommonUtils.getString(R.string.app_name)}-${CommonUtils.getString(R.string.str_toast)}")
            }

            override fun deleteContent() {
                ToastUtils.showToast(this@VerificationActivity,
                    CommonUtils.getString(R.string.str_clear))
            }
        })
    }

    private fun initView() {
        tvTitle.text = getString(R.string.str_verfication_code)
    }

    /**
     * 单次时取消加载框 连续时退出页面
     */
    override fun onBackPressed() {
        if ((System.currentTimeMillis() - doubleDuration) > 2 * 1000) {
            doubleDuration = System.currentTimeMillis()
            loadFinished()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, VerificationActivity::class.java))
        }
    }
}