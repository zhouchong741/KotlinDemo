package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.ui.fragment.AlreadyReadFragment
import com.kotlin.demo.ui.fragment.NotReadFragment
import com.kotlin.demo.util.CommonUtils
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class ArticleActivity : BaseActivity() {
    private var fragmentList: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        initView()
        addEvents()
    }

    private fun addEvents() {
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    private fun initView() {
        tvTitle.text = CommonUtils.getString(R.string.str_article)
        val titles: Array<String> = resources.getStringArray(R.array.read_array)
        tabLayout.setTabData(titles)
        fragmentList = arrayListOf()

        val alreadyReadFragment = AlreadyReadFragment.newInstance()
        val notReadFragment = NotReadFragment.newInstance()

        fragmentList!!.add(alreadyReadFragment)
        fragmentList!!.add(notReadFragment)

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        viewPager.offscreenPageLimit = fragmentList!!.size
        viewPager.currentItem = 0
    }

    @Suppress("DEPRECATION")
    inner class MyPagerAdapter(
        supportFragmentManager: FragmentManager,
    ) : FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getCount(): Int {
            return fragmentList!!.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList!![position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ArticleActivity::class.java))
        }
    }

}