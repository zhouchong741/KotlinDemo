package com.kotlin.demo.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.demo.R
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.CommonUtils
import com.kotlin.demo.util.ToastUtils
//import kotlinx.android.synthetic.main.layout_view_pager_title_bar.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/4 17:02
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
abstract class BaseVPFragment : BaseFragment() {
    protected var viewPager: ViewPager2? = null

    protected var tabLayout: CommonTabLayout? = null

    private var pageChangeCallback: PageChangeCallback? = null

    protected val adapter: VpAdapter by lazy {
        VpAdapter(requireActivity()).apply {
            addFragments(
                createFragments
            )
        }
    }

    private var offscreenPageLimit = 1

    abstract val createTitles: ArrayList<CustomTabEntity>

    abstract val createFragments: Array<Fragment>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback?.run { viewPager?.unregisterOnPageChangeCallback(this) }
        pageChangeCallback = null
    }

    open fun setupViews() {
        initViewPager()
//        ClickUtil.setOnClickListener(ivBack, ivSearch) {
//            ToastUtils.showToast(context, CommonUtils.getString(R.string.not_open_yet))
////            if (this == ivBack) {
////            } else if (this == ivSearch) {
////                SearchFragment.switchFragment(activity)
////            }
//        }
    }

    private fun initViewPager() {
        viewPager = rootView.findViewById(R.id.viewPager)
        tabLayout = rootView.findViewById(R.id.tabLayout)

        viewPager?.offscreenPageLimit = offscreenPageLimit
        viewPager?.adapter = adapter
        tabLayout?.setTabData(createTitles)
        tabLayout?.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        pageChangeCallback = PageChangeCallback()
        viewPager?.registerOnPageChangeCallback(pageChangeCallback!!)
    }

    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout?.currentTab = position
        }
    }

    inner class VpAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }
}