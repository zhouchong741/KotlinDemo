package com.kotlin.demo.ui.fragment

import android.os.Bundle
import android.view.View
import com.kotlin.demo.base.BaseViewBindingFragment
import com.kotlin.demo.databinding.FragmentMineBinding

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:23
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MineFragment : BaseViewBindingFragment() {

    private lateinit var viewBinding: FragmentMineBinding

    override fun getViewBindingLayoutResId(): View {
        viewBinding = FragmentMineBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewBinding.floatButton.setOnClickListener {
//            ToastUtils.showToast(activity, getString(R.string.not_open_yet))
        }
    }
}