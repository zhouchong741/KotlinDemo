package com.kotlin.demo.ui.activity.room

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.extension.logD
import com.kotlin.demo.gank.RoomViewModel
import com.kotlin.demo.livedata.observeOnce
import com.kotlin.demo.model.User
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.item_test.*


class RoomActivity : BaseActivity() {
    private val TAG = this.javaClass.simpleName
    private var currentId = 0
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectUtil.getRoomFactory()
        ).get(RoomViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_room
    }

    override fun initView() {
        tvTitle.text = getString(R.string.room)

        ClickUtil.setOnClickListener(btnWrite, btnRead, btnUpdate) {
            when (this) {
                btnWrite -> {
                    if (etName.text.toString().isNotEmpty()) {
                        viewModel.insert(
                            User(
                                0,
                                etName.text.toString(),
                                etAge.text.toString().toInt()
                            )
                        )
                    } else {
                        ToastUtils.showToast(this@RoomActivity, getString(R.string.can_not_be_empty))
                    }
                }
                btnRead -> {
                    if (etName.text.toString().isNotEmpty()) {
                        // 查询全部
                        viewModel.getAllUser.observe(this@RoomActivity, Observer {
                            ToastUtils.showToast(
                                this@RoomActivity,
                                "name=" + it[0].userName + ";age=" + it[0].age
                            )
                            logD(TAG, it.toString())
                        })
                        // 查询单个
                        viewModel.getUser(etName.text.toString()).observe(this@RoomActivity, Observer {
                            ToastUtils.showToast(this@RoomActivity, "name=" + it.userName + ";age=" + it.age)
                            currentId = it.id
                            logD(TAG, it.toString())
                        })
                    } else {
                        ToastUtils.showToast(this@RoomActivity, getString(R.string.can_not_be_empty))
                    }
                }
                btnUpdate -> {
                    if (etName.text.toString().isNotEmpty()) {
                        // 使用 observeOnce 只获取一次观察结果
                        viewModel.getUserById(currentId).observeOnce(this@RoomActivity, Observer {
                            it.age = etAge.text.toString().toInt()
                            viewModel.updateUser(it)
                        })
                        // suspend 方法获取 user
                        val user: User = viewModel.getUserByIdOnce(currentId)
                        user.age = etAge.text.toString().toInt()
                        viewModel.updateUser(user)
                    } else {
                        ToastUtils.showToast(this@RoomActivity, getString(R.string.can_not_be_empty))
                    }
                }
            }
        }

    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, RoomActivity::class.java))
        }
    }


}