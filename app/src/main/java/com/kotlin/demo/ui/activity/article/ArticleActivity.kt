package com.kotlin.demo.ui.activity.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingIntActivity
import com.kotlin.demo.databinding.ActivityArticleBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.entity.TabEntity
import com.kotlin.demo.ui.fragment.AlreadyReadFragment
import com.kotlin.demo.ui.fragment.NotReadFragment
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.ToastUtils

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:19
 * 描述： 文章页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ArticleActivity : BaseViewBindingIntActivity() {
    private lateinit var viewBinding: ActivityArticleBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding

    // 通用 统一定义 Fragment
    private var fragmentList: Array<Fragment> =
        arrayOf(AlreadyReadFragment(this), NotReadFragment(this))

    override fun getNormalLayoutResId(): Int = 0

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityArticleBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    override fun initView() {
        initView2()
        // initView1()
        search()
    }

    private fun search() {
        includeViewBinding.tvRightText.visibility = View.VISIBLE
        includeViewBinding.tvRightText.text = getString(R.string.share)
        includeViewBinding.ivRight.visibility = View.VISIBLE
        includeViewBinding.ivRight.setImageResource(R.mipmap.ic_search)
        includeViewBinding.ivRight.setOnClickListener {
            ToastUtils.showToast(this, getString(R.string.not_open_yet))
        }
        includeViewBinding.tvRightText.setOnClickListener {
            ToastUtils.showToast(this, getString(R.string.not_open_yet))
        }
    }

    /**
     * ViewPager xml 需要使用 ViewPager
     */
    /*private fun initView1() {
        tvTitle.text = CommonUtils.getString(R.string.str_article)
        val titles: Array<String> = resources.getStringArray(R.array.read_array)
        val tabs: ArrayList<CustomTabEntity> = arrayListOf()
        tabs.add(TabEntity(titles[0], 0))
        tabs.add(TabEntity(titles[1], 0))
        tabLayout.setTabData(tabs)

        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {}

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        viewPager.offscreenPageLimit = fragmentList.size
        viewPager.currentItem = 0
    }

    @Suppress("DEPRECATION")
    inner class MyPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(supportFragmentManager) {

        override fun getCount() = fragmentList.size

        override fun getItem(position: Int) = fragmentList[position]

        override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE
    }*/

    /**
     * ViewPager2 xml 需要使用 ViewPager2
     */
    private fun initView2() {
        includeViewBinding.tvTitle.text = CommonUtils.getString(R.string.str_article)

        val titles: Array<String> = resources.getStringArray(R.array.read_array)
        val createTitles = ArrayList<CustomTabEntity>().apply {
            add(TabEntity(titles[0], 0, 0))
            add(TabEntity(titles[1], 0, 0))
        }
        // lazy 只获取 不赋值 常量
        val adapter: ViewPager2Adapter by lazy {
            ViewPager2Adapter(this).apply {
                addFragments(
                    fragmentList
                )
            }
        }
        // 等同于下面的写法
//        val adapter =  ViewPager2Adapter(this)
//        adapter.addFragments(fragmentList)
        viewBinding.viewPager.offscreenPageLimit = 1
        viewBinding.viewPager.adapter = adapter
        viewBinding.tabLayout.setTabData(createTitles)
        // 监听
        viewBinding.tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewBinding.viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })

        viewBinding.viewPager.registerOnPageChangeCallback(PageChangeCallback())
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
            viewBinding.tabLayout.currentTab = position
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ArticleActivity::class.java))
        }
    }

}