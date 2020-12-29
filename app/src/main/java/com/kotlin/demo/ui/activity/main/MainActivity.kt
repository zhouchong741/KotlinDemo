package com.kotlin.demo.ui.activity.main

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityMain2Binding
import com.kotlin.demo.entity.TabEntity
import com.kotlin.demo.ui.fragment.GanHuoFragment
import com.kotlin.demo.ui.fragment.MainFragment
import com.kotlin.demo.ui.fragment.MeiZiFragment
import com.kotlin.demo.ui.fragment.MineFragment
import com.kotlin.demo.util.ToastUtils
import kotlinx.coroutines.Job

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:20
 * 描述: 项目主页
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class MainActivity : BaseViewBindingActivity() {
    private lateinit var viewBinding: ActivityMain2Binding
    private val job by lazy { Job() }
    private val TAG = this.javaClass.simpleName
    private var doubleDuration = 0L
    private var fragmentList: Array<Fragment> =
        arrayOf(
            MainFragment(),
            GanHuoFragment(),
            MeiZiFragment(),
            MineFragment()
        )

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityMain2Binding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        val titles: Array<String> = resources.getStringArray(R.array.title_array)
        val selectIds: TypedArray = resources.obtainTypedArray(R.array.select_icon_array)
        val unSelectIds: TypedArray = resources.obtainTypedArray(R.array.unselect_icon_array)
        val titleData = ArrayList<CustomTabEntity>()
        for (i: Int in titles.indices) {
            titleData.add(
                TabEntity(
                    titles[i],
                    selectIds.getResourceId(i, 0),
                    unSelectIds.getResourceId(i, 0)
                )
            )
        }
        val adapter: ViewPager2Adapter by lazy {
            ViewPager2Adapter(this).apply {
                addFragment(fragmentList)
            }
        }
        viewBinding.homeViewPager.adapter = adapter
        viewBinding.homeViewPager.offscreenPageLimit = 1
        viewBinding.bottomTabLayout.setTabData(titleData)
        viewBinding.bottomTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewBinding.homeViewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })
        viewBinding.homeViewPager.registerOnPageChangeCallback(PageChangeCallBack())
        // 禁止滑动
        viewBinding.homeViewPager.isUserInputEnabled = false
    }

    inner class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val fragments = arrayListOf<Fragment>()
        fun addFragment(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment = fragmentList[position]
    }

    inner class PageChangeCallBack : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewBinding.bottomTabLayout.currentTab = position
        }
    }

    /**
     * 连续退出页面
     */
    override fun onBackPressed() {
        if ((System.currentTimeMillis() - doubleDuration) > 2 * 1000) {
            doubleDuration = System.currentTimeMillis()
            ToastUtils.showToast(this, getString(R.string.str_back_double_press))
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}