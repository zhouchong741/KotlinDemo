package com.kotlin.demo.wigets.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlin.demo.R
import com.kotlin.demo.ui.activity.picture.PictureActivity
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.Constant
import com.kotlin.demo.util.ShareUtils.share
import kotlinx.android.synthetic.main.share_bottom_dialog.*

/**
 * @author: zhouchong
 * 创建日期: 2020/11/4-15:18
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ShareBottomDialog(private val activity: PictureActivity) : BottomSheetDialogFragment() {
    private lateinit var shareContent: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.share_bottom_dialog, container, false)
    }

    // 必须在这个地方使用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ClickUtil.setOnClickListener(tvQQ, tvWeChat, tvMoments, tvQQZone, tvMore, tvCancel) {
            when (this) {
                tvQQ -> {
                    share(activity, shareContent, Constant.SHARE_QQ)
                    dismiss()
                }
                tvWeChat -> {
                    share(activity, shareContent, Constant.SHARE_WECHAT)
                    dismiss()
                }
                tvMoments -> {
                    share(activity, shareContent, Constant.SHARE_WECHAT_MEMORIES)
                    dismiss()
                }
                tvQQZone -> {
                    share(activity, shareContent, Constant.SHARE_QQZONE)
                    dismiss()
                }
                tvMore -> {
                    share(activity, shareContent, Constant.SHARE_MORE)
                    dismiss()
                }
                tvCancel -> {
                    dismiss()
                }
            }
        }
    }

    fun showDialog(activity: AppCompatActivity, shareContent: String) {
        show(activity.supportFragmentManager, "share_dialog")
        this.shareContent = shareContent
    }
}