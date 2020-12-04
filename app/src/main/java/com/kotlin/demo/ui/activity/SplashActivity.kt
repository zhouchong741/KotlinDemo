package com.kotlin.demo.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivitySplashBinding
import com.kotlin.demo.util.SharePreferenceUtils
import com.kotlin.demo.util.SharePreferenceUtils.edit
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:17
 * 描述：闪屏页
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class SplashActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivitySplashBinding

    private val job by lazy { Job() }

    private val durationTime = 4000L
    private val delayTime = 5000L

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        if (isFirstEnterApp) {
            setAnim
            CoroutineScope(job).launch {
                delay(delayTime)
                LoginActivity.startActivity(this@SplashActivity)
                finish()
                isFirstEnterApp = false
            }
        } else {
            LoginActivity.startActivity(this@SplashActivity)
            finish()
        }
    }

    private val setAnim by lazy {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofPropertyValuesHolder(
                    viewBinding.tvHello,
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                    PropertyValuesHolder.ofFloat("ScaleX", 1f, 2f),
                    PropertyValuesHolder.ofFloat("ScaleY", 1f, 2f),
                    PropertyValuesHolder.ofFloat("translationY", 0f, 100f).apply {
                        interpolator = AccelerateInterpolator()
                        duration = durationTime
                    }
                ),
                ObjectAnimator.ofPropertyValuesHolder(
                    viewBinding.ivHeader,
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                    PropertyValuesHolder.ofFloat("ScaleX", 1f, 1.5f),
                    PropertyValuesHolder.ofFloat("ScaleY", 1f, 1.5f),
                ).apply {
                    interpolator = AccelerateInterpolator()
                    duration = durationTime
                }
            )
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        var isFirstEnterApp: Boolean
            get() = SharePreferenceUtils.sharedPreferences.getBoolean("is_first_enter_app", true)
            set(value) = edit { putBoolean("is_first_enter_app", value) }
    }
}

