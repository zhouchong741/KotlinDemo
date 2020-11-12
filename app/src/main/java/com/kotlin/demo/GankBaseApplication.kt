package com.kotlin.demo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.kotlin.demo.extension.logD
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 11:03
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
class GankBaseApplication : Application() {

    init {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableAutoLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
            // 设置是否启在下拉 Header 的同时下拉内容
            layout.setEnableHeaderTranslationContent(false)
            MaterialHeader(context).setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent
            )
        }

//        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
//            layout.setEnableFooterFollowWhenNoMoreData(false)
//            layout.setEnableFooterTranslationContent(false)
//            layout.setFooterHeight(100f)
//            layout.setFooterTriggerRate(0.6f)
//            ClassicsFooter(context).apply {
//                setAccentColorId(R.color.colorAccent)
//                setTextSizeTitle(15f)
//            }
//        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        // mmkv
        val rootDir: String = MMKV.initialize(this)
        // /data/user/0/com.kotlin.demo/files/mmkv
        logD("KotlinDemo", rootDir)
    }

    companion object {
        lateinit var context: Context
    }
}