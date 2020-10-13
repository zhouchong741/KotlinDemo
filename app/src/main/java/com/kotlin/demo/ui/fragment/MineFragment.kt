package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseFragment
import com.kotlin.demo.ui.activity.main.Main2Activity
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MineFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_mine,
            container,
            false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        floatButton.setOnClickListener {
//            ToastUtils.showToast(activity, getString(R.string.not_open_yet))
        }
    }
}