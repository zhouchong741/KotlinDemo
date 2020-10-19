package com.kotlin.demo.ui.activity.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.database.UserDatabase
import com.kotlin.demo.gank.RoomViewModel
import com.kotlin.demo.model.User
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.item_test.*

class RoomActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectUtil.getRoomFactory()
        ).get(RoomViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        initView()

    }

    private fun initView() {
        tvTitle.text = getString(R.string.room)

        // insert
        btnWrite.setOnClickListener {
            if (etName.text.toString().isNotEmpty()) {
                viewModel.insert(User(0, etName.text.toString(), etAge.text.toString().toInt()))
            } else {
                ToastUtils.showToast(this, getString(R.string.can_not_be_empty))
            }
        }

        btnRead.setOnClickListener {
            if (etName.text.toString().isNotEmpty()) {
                val user = viewModel.query(etName.text.toString())
                ToastUtils.showToast(this, "name=" + user.userName + ";age=" + user.age)
            } else {
                ToastUtils.showToast(this, getString(R.string.can_not_be_empty))
            }

        }
    }


    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, RoomActivity::class.java))
        }
    }
}