package com.kotlin.demo.ui.activity

import android.animation.*
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.impl.AnimatorListenerImpl
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val job by lazy { Job() }

    private val durationTime = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        tvHello.startAnimation(scaleAnimation)
//        tvHello.startAnimation(rotateAnimation)
        setAnim
        CoroutineScope(job).launch {
            delay(durationTime)
            MainActivity.startActivity(this@SplashActivity)
        }
    }

    private val rotateAnimation by lazy {
        RotateAnimation(
            0f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = durationTime
            fillAfter = true
        }
    }

    private val scaleAnimation by lazy {
        ScaleAnimation(
            1f,
            2f,
            1f,
            2f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = durationTime
            fillAfter = true
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
            addPauseListener(object : Animator.AnimatorPauseListener {
                override fun onAnimationPause(p0: Animator?) {
                    Toast.makeText(applicationContext, "pause", Toast.LENGTH_SHORT).show()
                }

                override fun onAnimationResume(p0: Animator?) {

                }
            })

//            addListener(object : Animator.AnimatorListener {
//                override fun onAnimationRepeat(animation: Animator?) {
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    Toast.makeText(applicationContext, "end", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                    Toast.makeText(applicationContext, "start", Toast.LENGTH_SHORT).show()
//                }
//            })


            addChangeListener {
                onStart = {
                    Toast.makeText(applicationContext, "start", Toast.LENGTH_SHORT).show()
                }
            }
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

    }
}

