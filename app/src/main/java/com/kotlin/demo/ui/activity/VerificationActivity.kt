package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityVerificationBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.KeyboardUtil
import com.kotlin.demo.util.ToastUtils
import com.kotlin.demo.wigets.verification.VerificationCodeView.InputCompleteListener

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:17
 * 描述： 验证码页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class VerificationActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityVerificationBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding
    private var doubleDuration = 0L
    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityVerificationBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    override fun initView() {
        includeViewBinding.tvTitle.text = getString(R.string.str_verification_code)

        viewBinding.clearVerification.setOnClickListener {
            viewBinding.verificationCodeView.clearInputContent()
        }
        viewBinding.verificationCodeView.setInputCompleteListener(object : InputCompleteListener {
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