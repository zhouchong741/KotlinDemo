package com.kotlin.demo.ui.activity.room

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityRoomBinding
import com.kotlin.demo.databinding.LayoutTitleBarBinding
import com.kotlin.demo.extension.logD
import com.kotlin.demo.gank.RoomViewModel
import com.kotlin.demo.livedata.observeOnce
import com.kotlin.demo.model.User
import com.kotlin.demo.util.ClickUtil
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils

/**
 * @author zhouchong
 * 创建日期: 2020/12/15 13:20
 * 描述：Room 学习使用
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class RoomActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityRoomBinding
    private lateinit var includeViewBinding: LayoutTitleBarBinding
    private val TAG = this.javaClass.simpleName
    private var currentId = 0
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectUtil.getRoomFactory()
        ).get(RoomViewModel::class.java)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityRoomBinding.inflate(layoutInflater)
        includeViewBinding = viewBinding.include
        return viewBinding.root
    }

    override fun initView() {
        includeViewBinding.tvTitle.text = getString(R.string.room)

        ClickUtil.setOnClickListener(viewBinding.btnWrite,
            viewBinding.btnRead,
            viewBinding.btnUpdate) {
            when (this) {
                viewBinding.btnWrite -> {
                    if (viewBinding.etName.text.toString().isNotEmpty()) {
                        viewModel.insert(
                            User(
                                0,
                                viewBinding.etName.text.toString(),
                                viewBinding.etAge.text.toString().toInt()
                            )
                        )
                    } else {
                        ToastUtils.showToast(this@RoomActivity,
                            getString(R.string.can_not_be_empty))
                    }
                }
                viewBinding.btnRead -> {
                    if (viewBinding.etName.text.toString().isNotEmpty()) {
                        // 查询全部
                        viewModel.getAllUser.observe(this@RoomActivity, Observer {
                            ToastUtils.showToast(
                                this@RoomActivity,
                                "name=" + it[0].userName + ";age=" + it[0].age
                            )
                            logD(TAG, it.toString())
                        })
                        // 查询单个
                        viewModel.getUser(viewBinding.etName.text.toString())
                            .observe(this@RoomActivity, Observer {
                                ToastUtils.showToast(this@RoomActivity,
                                    "name=" + it.userName + ";age=" + it.age)
                                currentId = it.id
                                logD(TAG, it.toString())
                            })
                    } else {
                        ToastUtils.showToast(this@RoomActivity,
                            getString(R.string.can_not_be_empty))
                    }
                }
                viewBinding.btnUpdate -> {
                    if (viewBinding.etName.text.toString().isNotEmpty()) {
                        // 使用 observeOnce 只获取一次观察结果
                        viewModel.getUserById(currentId).observeOnce(this@RoomActivity, Observer {
                            it.age = viewBinding.etAge.text.toString().toInt()
                            viewModel.updateUser(it)
                        })
                        // suspend 方法获取 user
                        val user: User = viewModel.getUserByIdOnce(currentId)
                        user.age = viewBinding.etAge.text.toString().toInt()
                        viewModel.updateUser(user)
                    } else {
                        ToastUtils.showToast(this@RoomActivity,
                            getString(R.string.can_not_be_empty))
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