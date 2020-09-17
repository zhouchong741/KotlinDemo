package com.kotlin.demo.impl

import android.animation.Animator

/**
 * @author: zhouchong
 * 创建日期: 2020/9/1 15:59
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class AnimatorListenerImpl {
    var onRepeat: ((Animator) -> Unit)? = null
    var onEnd: ((Animator) -> Unit)? = null
    var onCancel: ((Animator) -> Unit)? = null
    var onStart: ((Animator) -> Unit)? = null

}