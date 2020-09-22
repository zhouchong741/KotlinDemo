package com.kotlin.demo.ui.activity.main

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.entity.TabEntity
import com.kotlin.demo.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main2.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 10:20
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class Main2Activity : BaseActivity() {

    private var fragmentList: Array<Fragment> =
        arrayOf(MainFragment(),
            GanHuoFragment(this),
            MeiZiFragment(this),
            MineFragment(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initView()
    }

    private fun initView() {
        val titles: Array<String> = resources.getStringArray(R.array.title_array)
        val selectIds: TypedArray = resources.obtainTypedArray(R.array.select_icon_array)
        val unSelectIds: TypedArray = resources.obtainTypedArray(R.array.unselect_icon_array)
        val titleData = ArrayList<CustomTabEntity>()
        for (i: Int in titles.indices) {
            titleData.add(TabEntity(titles[i],
                selectIds.getResourceId(i, 0),
                unSelectIds.getResourceId(i, 0)))
        }

        val adapter: ViewPager2Adapter by lazy {
            ViewPager2Adapter(this).apply {
                addFragment(fragmentList)
            }
        }

        homeViewPager.adapter = adapter
        homeViewPager.offscreenPageLimit = 1
        bottomTabLayout.setTabData(titleData)
        bottomTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                homeViewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })

        homeViewPager.registerOnPageChangeCallback(PageChangeCallBack())
    }

    inner class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val fragments = mutableListOf<Fragment>()
        fun addFragment(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment = fragmentList[position]
    }

    inner class PageChangeCallBack : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            bottomTabLayout.currentTab = position
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, Main2Activity::class.java))
        }
    }
}