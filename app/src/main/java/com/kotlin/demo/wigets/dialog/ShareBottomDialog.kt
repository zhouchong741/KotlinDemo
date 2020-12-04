package com.kotlin.demo.wigets.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlin.demo.R
import com.kotlin.demo.databinding.ShareBottomDialogBinding
import com.kotlin.demo.ui.activity.picture.PictureActivity
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.Constant
import com.kotlin.demo.util.ShareUtils.share

/**
 * @author: zhouchong
 * 创建日期: 2020/11/4-15:18
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class ShareBottomDialog(private val activity: PictureActivity) : BottomSheetDialogFragment() {

    private lateinit var viewBinding: ShareBottomDialogBinding
    private lateinit var shareContent: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = ShareBottomDialogBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    // 必须在这个地方使用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ClickUtil.setOnClickListener(viewBinding.tvQQ,
            viewBinding.tvWeChat,
            viewBinding.tvMoments,
            viewBinding.tvQQZone,
            viewBinding.tvMore,
            viewBinding.tvCancel) {
            when (this) {
                viewBinding.tvQQ -> {
                    share(activity, shareContent, Constant.SHARE_QQ)
                    dismiss()
                }
                viewBinding.tvWeChat -> {
                    share(activity, shareContent, Constant.SHARE_WECHAT)
                    dismiss()
                }
                viewBinding.tvMoments -> {
                    share(activity, shareContent, Constant.SHARE_WECHAT_MEMORIES)
                    dismiss()
                }
                viewBinding.tvQQZone -> {
                    share(activity, shareContent, Constant.SHARE_QQZONE)
                    dismiss()
                }
                viewBinding.tvMore -> {
                    share(activity, shareContent, Constant.SHARE_MORE)
                    dismiss()
                }
                viewBinding.tvCancel -> {
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