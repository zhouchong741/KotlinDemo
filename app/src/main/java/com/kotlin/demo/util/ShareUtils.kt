package com.kotlin.demo.util

import android.app.Activity
import android.content.Intent
import com.kotlin.demo.R
import com.kotlin.demo.util.Constant.SHARE_MORE
import com.kotlin.demo.util.Constant.SHARE_QQ
import com.kotlin.demo.util.Constant.SHARE_QQZONE
import com.kotlin.demo.util.Constant.SHARE_WECHAT
import com.kotlin.demo.util.Constant.SHARE_WECHAT_MEMORIES
import com.kotlin.demo.util.Constant.SHARE_WEIBO
import com.kotlin.demo.util.ToastUtils.showToast

/**
 * @author: zhouchong
 * 创建日期: 2020/11/4-15:55
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
object ShareUtils {

    private fun processShare(activity: Activity, shareContent: String, shareType: Int) {
        when (shareType) {
            SHARE_QQ -> {
                if (!CommonUtils.isQQInstalled()) {
                    showToast(activity, CommonUtils.getString(R.string.your_phone_does_not_install_qq))
                    return
                }
                share(activity, shareContent, "com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity")
            }
            SHARE_WECHAT -> {
                if (!CommonUtils.isWechatInstalled()) {
                    CommonUtils.getString(R.string.your_phone_does_not_install_wechat).showToast()
                    return
                }
                share(activity, shareContent, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
            }
            SHARE_WECHAT_MEMORIES -> {
                if (!CommonUtils.isWechatInstalled()) {
                    CommonUtils.getString(R.string.your_phone_does_not_install_wechat).showToast()
                    return
                }
                share(activity, shareContent, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            }
            SHARE_WEIBO -> {
                if (!CommonUtils.isWeiboInstalled()) {
                    CommonUtils.getString(R.string.your_phone_does_not_install_weibo).showToast()
                    return
                }
                share(activity, shareContent, "com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity")
            }
            SHARE_QQZONE -> {
                if (!CommonUtils.isWeiboInstalled()) {
                    CommonUtils.getString(R.string.your_phone_does_not_install_qq_zone).showToast()
                    return
                }
                share(activity, shareContent, "com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity")
            }
            SHARE_MORE -> {
                share(activity, shareContent)
            }
        }
    }

    private fun share(activity: Activity, shareContent: String, packageName: String, className: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareContent)
                setClassName(packageName, className)
            }
            activity.startActivity(shareIntent)
        } catch (e: Exception) {
            CommonUtils.getString(R.string.share_unknown_error).showToast()
        }
    }

    fun share(activity: Activity, shareContent: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareContent)
        }
        activity.startActivity(Intent.createChooser(shareIntent, CommonUtils.getString(R.string.share_to)))
    }

    /**
     * 调用系统原生分享
     *
     * @param shareContent 分享内容
     * @param shareType SHARE_MORE=0，SHARE_QQ=1，SHARE_WECHAT=2，SHARE_WEIBO=3，SHARE_QQZONE=4
     */
    fun share(activity: Activity, shareContent: String, shareType: Int) {
        processShare(activity, shareContent, shareType)
    }
}