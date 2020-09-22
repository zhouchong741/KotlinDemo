package com.kotlin.demo.ui.activity

import android.animation.*
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.impl.AnimatorListenerImpl
import com.kotlin.demo.ui.activity.main.Main2Activity
import com.kotlin.demo.ui.activity.main.MainActivity
import com.kotlin.demo.util.SharePreferenceUtils
import com.kotlin.demo.util.SharePreferenceUtils.edit
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val job by lazy { Job() }

    private val durationTime = 4000L
    private val delayTime = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 首次进入判断
        if (isFirstEnterApp) {
            setAnim
            CoroutineScope(job).launch {
                delay(delayTime)
                MainActivity.startActivity(this@SplashActivity)
                finish()
                isFirstEnterApp = false
            }
        } else {
            Main2Activity.startActivity(this@SplashActivity)
            finish()
        }
    }

    private val setAnim by lazy {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofPropertyValuesHolder(
                    tvHello,
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                    PropertyValuesHolder.ofFloat("ScaleX", 1f, 2f),
                    PropertyValuesHolder.ofFloat("ScaleY", 1f, 2f),
                    PropertyValuesHolder.ofFloat("translationY", 0f, 100f).apply {
                        interpolator = AccelerateInterpolator()
                        duration = durationTime
                    }
                ),
                ObjectAnimator.ofPropertyValuesHolder(
                    ivHeader,
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                    PropertyValuesHolder.ofFloat("translationY", 0f, 100f)
                ).apply {
                    interpolator = AccelerateInterpolator()
                    duration = durationTime
                }
            )
            start()
        }
    }

    private fun AnimatorSet.addChangeListener(action: AnimatorListenerImpl.() -> Unit) {
        AnimatorListenerImpl().apply { action }.let { builder ->
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    animation?.let { builder.onRepeat?.invoke(animation) }
                }

                override fun onAnimationEnd(animation: Animator?) {
                    animation?.let { builder.onEnd?.invoke(animation) }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    animation?.let { builder.onCancel?.invoke(animation) }
                }

                override fun onAnimationStart(animation: Animator?) {
                    animation?.let { builder.onStart?.invoke(animation) }
                }
            })
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

