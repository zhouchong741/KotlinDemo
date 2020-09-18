package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.entity.TabEntity
import com.kotlin.demo.ui.fragment.AlreadyReadFragment
import com.kotlin.demo.ui.fragment.NotReadFragment
import com.kotlin.demo.util.CommonUtils
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class ArticleActivity : BaseActivity() {
    // 通用 统一定义 Fragment
    private var fragmentList: Array<Fragment> =
        arrayOf(AlreadyReadFragment(this), NotReadFragment(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
//        initView1()
        initView2()
    }

    /**
     * ViewPager xml 需要使用 ViewPager
     */
//    private fun initView1() {
//        tvTitle.text = CommonUtils.getString(R.string.str_article)
//        val titles: Array<String> = resources.getStringArray(R.array.read_array)
//        val tabs: ArrayList<CustomTabEntity> = arrayListOf()
//        tabs.add(TabEntity(titles[0], 0))
//        tabs.add(TabEntity(titles[1], 0))
//        tabLayout.setTabData(tabs)
//
//        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
//            override fun onTabSelect(position: Int) {
//                viewPager.currentItem = position
//            }
//
//            override fun onTabReselect(position: Int) {
//
//            }
//        })
//
//        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int,
//            ) {}
//
//            override fun onPageSelected(position: Int) {
//                tabLayout.currentTab = position
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })
//        viewPager.offscreenPageLimit = fragmentList.size
//        viewPager.currentItem = 0
//    }

//    @Suppress("DEPRECATION")
//    inner class MyPagerAdapter(supportFragmentManager: FragmentManager) :
//        FragmentStatePagerAdapter(supportFragmentManager) {
//
//        override fun getCount() = fragmentList.size
//
//        override fun getItem(position: Int) = fragmentList[position]
//
//        override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE
//    }

    /**
     * ViewPager2 xml 需要使用 ViewPager2
     */
    private fun initView2() {
        tvTitle.text = CommonUtils.getString(R.string.str_article)

        val titles: Array<String> = resources.getStringArray(R.array.read_array)
        val createTitles = ArrayList<CustomTabEntity>().apply {
            add(TabEntity(titles[0], 0))
            add(TabEntity(titles[1], 0))
        }
        val adapter: ViewPager2Adapter by lazy {
            ViewPager2Adapter(this).apply {
                addFragments(
                    fragmentList
                )
            }
        }
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = adapter
        tabLayout.setTabData(createTitles)
        // 监听
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })

        viewPager.registerOnPageChangeCallback(PageChangeCallback())
    }

    inner class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

    // 回调
    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout?.currentTab = position
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ArticleActivity::class.java))
        }
    }

}