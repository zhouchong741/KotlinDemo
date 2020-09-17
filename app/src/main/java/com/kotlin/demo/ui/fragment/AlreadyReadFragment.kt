package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseFragment

/**
 * @author: zhouchong
 * 创建日期: 2020/9/17 19:16
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class AlreadyReadFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_already_read,
            container,
            false))
    }

    companion object {
        fun newInstance() = AlreadyReadFragment()
    }

}