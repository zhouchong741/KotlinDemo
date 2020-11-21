package com.kotlin.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author: zhouchong
 * 创建日期: 2020/11/19-19:11
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
abstract class BaseFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutResId(), container, false)
        initView(rootView)
        return rootView
    }

    open fun initView(rootView: View) {

    }


    abstract fun getLayoutResId(): Int
}