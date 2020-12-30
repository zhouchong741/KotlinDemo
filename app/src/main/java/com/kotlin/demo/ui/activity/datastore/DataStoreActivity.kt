package com.kotlin.demo.ui.activity.datastore

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityDataStoreBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.gank.DataStoreViewModel
import com.kotlin.demo.gank.DataStoreViewModel.PreferencesKeys
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 20:02
 * 描述： DataStore 数据存储 代替 SharePreference
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DataStoreActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityDataStoreBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectUtil.getDataStoreFactory()
        ).get(DataStoreViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityDataStoreBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

//    override fun getViewBindingLayoutResId(): View? {
//        return super.getViewBindingLayoutResId()
//    }


    override fun initView() {
        includeViewBinding.tvTitle.text = getString(R.string.data_store)

        ClickUtil.setOnClickListener(viewBinding.btnWrite, viewBinding.btnRead) {
            when (this) {
                viewBinding.btnWrite -> {
//                    viewModel.saveDataByDataStore(PreferencesKeys.KEY_GITHUB)
                    suspend {
                        viewModel.saveStringData("Hello World")
                    }
                }
                viewBinding.btnRead -> {
//                    viewModel.getDataByDataStore(PreferencesKeys.KEY_GITHUB)
//                        .observe(this@DataStoreActivity) {
//                            ToastUtils.showToast(this@DataStoreActivity, it.toString())
//                        }

                    viewModel.getStringData(PreferencesKeys.STRING_KEY)
                        .observe(this@DataStoreActivity) {
                            ToastUtils.showToast(this@DataStoreActivity, it)
                        }

                }
            }
        }

    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, DataStoreActivity::class.java))
        }
    }

}
