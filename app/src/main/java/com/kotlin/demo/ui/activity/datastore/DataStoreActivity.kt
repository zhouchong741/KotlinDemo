package com.kotlin.demo.ui.activity.datastore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.gank.DataStoreViewModel
import com.kotlin.demo.gank.DataStoreViewModel.PreferencesKeys
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)

        initView()
    }

    private fun initView() {
        tvTitle.text = getString(R.string.data_store)
        btnWrite.setOnClickListener {
            viewModel.saveDataByDataStore(PreferencesKeys.KEY_GITHUB)
        }
        btnRead.setOnClickListener {
            viewModel.getDataByDataStore(PreferencesKeys.KEY_GITHUB).observe(this) {
                ToastUtils.showToast(this, it.toString())
            }
        }
    }


    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, DataStoreActivity::class.java))
        }
    }
}
