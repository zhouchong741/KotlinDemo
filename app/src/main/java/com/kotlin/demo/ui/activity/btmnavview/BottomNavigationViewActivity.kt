package com.kotlin.demo.ui.activity.btmnavview

import android.content.Context
import android.content.Intent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
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
class BottomNavigationViewActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_bottom_navigation_view
    }

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentView) as NavHostFragment
        bottomNavView.setupWithNavController(navHostFragment.navController)
        // 添加点击事件不切换了 。。。
//        bottomNavView.setOnNavigationItemSelectedListener { it ->
//            when (it.itemId) {
//                R.id.mainFragment -> {
//                    ToastUtils.showToast(this, it.title.toString())
//                }
//                R.id.ganHuoFragment -> {
//                    ToastUtils.showToast(this, it.title.toString())
//                }
//                R.id.meiZiFragment -> {
//                    ToastUtils.showToast(this, it.title.toString())
//                }
//                R.id.mineFragment -> {
//                    ToastUtils.showToast(this, it.title.toString())
//                }
//            }
//            true
//        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, BottomNavigationViewActivity::class.java))
        }
    }
}