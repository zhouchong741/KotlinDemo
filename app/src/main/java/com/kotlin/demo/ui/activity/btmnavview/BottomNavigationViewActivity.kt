package com.kotlin.demo.ui.activity.btmnavview

import android.content.Context
import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingIntActivity
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_bottom_navigation_view.*

/**
 * @author zhouchong
 * 创建日期: 2020/11/21 13:49
 * 描述：BottomNavigationView 简单使用
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class BottomNavigationViewActivity : BaseViewBindingIntActivity() {

    override fun getNormalLayoutResId(): Int {
        return R.layout.activity_bottom_navigation_view
    }

    override fun initView() {
        // 切换点击的 view 绑定
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentView) as NavHostFragment
        bottomNavView.setupWithNavController(navHostFragment.navController)
        // 处理点击事件
        bottomNavView.setOnNavigationItemSelectedListener {
            val navController = Navigation.findNavController(navHostFragmentView)
            when (it.itemId) {
                R.id.mainFragment -> {
                    navController.navigate(R.id.mainFragment)
                    ToastUtils.showToast(this, it.title.toString())
                }
                R.id.ganHuoFragment -> {
                    navController.navigate(R.id.ganHuoFragment)
                    ToastUtils.showToast(this, it.title.toString())
                }
                R.id.meiZiFragment -> {
                    navController.navigate(R.id.meiZiFragment)
                    ToastUtils.showToast(this, it.title.toString())
                }
                R.id.mineFragment -> {
                    navController.navigate(R.id.mineFragment)
                    ToastUtils.showToast(this, it.title.toString())
                }
            }
            true
        }

        // 处理重复点击时页面刷新问题 看源码就清楚
        bottomNavView.setOnNavigationItemReselectedListener {

        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, BottomNavigationViewActivity::class.java))
        }
    }
}