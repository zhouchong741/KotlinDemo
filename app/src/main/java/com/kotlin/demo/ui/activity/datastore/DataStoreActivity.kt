package com.kotlin.demo.ui.activity.datastore

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.gank.DataStoreViewModel
import com.kotlin.demo.gank.DataStoreViewModel.PreferencesKeys
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.android.synthetic.main.item_test.*

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 20:02
 * 描述： DataStore 数据存储 代替 SharePreference
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class DataStoreActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectUtil.getDataStoreFactory()
        ).get(DataStoreViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_data_store
    }

    override fun initView() {
        tvTitle.text = getString(R.string.data_store)

        ClickUtil.setOnClickListener(btnWrite, btnRead) {
            when (this) {
                btnWrite -> {
//                    viewModel.saveDataByDataStore(PreferencesKeys.KEY_GITHUB)
                    suspend {
                        viewModel.saveStringData("Hello World")
                    }
                }
                btnRead -> {
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
