package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseViewBindingActivity
import com.kotlin.demo.databinding.ActivityLoginBinding
import com.kotlin.demo.gank.LoginViewModel
import com.kotlin.demo.param.LoginParams
import com.kotlin.demo.ui.activity.main.MainActivity
import com.kotlin.demo.ui.activity.shareelement.ShareElementActivity
import com.kotlin.demo.util.*

/**
 * @author zhouchong
 * 创建日期: 2020/10/15 9:16
 * 描述：登录页面
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
class LoginActivity : BaseViewBindingActivity() {

    private lateinit var viewBinding: ActivityLoginBinding
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.postLoginFactory()).get(LoginViewModel::class.java)
    }

    private fun setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    }

    override fun getViewBindingLayoutResId(): View {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initView() {
        setStatusBar()
        // 直接获取 mmkv
        viewBinding.userNameET.setText(mmkv.decodeString(Constant.USER_NAME))
        viewBinding.passwordET.setText(mmkv.decodeString(Constant.PASSWORD))
        viewBinding.btnLogin.setOnClickListener {
            if (viewBinding.userNameET.text.isNullOrBlank() || viewBinding.passwordET.text.isNullOrBlank()) {
                ToastUtils.showToast(this, getString(R.string.user_name_and_pwd_can_not_be_empty))
                return@setOnClickListener
            }
            viewBinding.lottieView.visibility = View.VISIBLE
            viewBinding.btnLogin.text = ""
            viewModel.onLogin(LoginParams(viewBinding.userNameET.text.toString(), viewBinding.passwordET.text.toString()))
            observe()
            /*原始未封装 Retrofit 请求
            val retrofit = Retrofit.Builder()
                .baseUrl("http://121.43.123.76/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val retrofitService = retrofit.create(MainPageService::class.java)
            val apiCall = retrofitService.login(userNameET.text.toString(), passwordET.text.toString())
            apiCall.enqueue(object : Callback<LoginModel?> {
                override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
                    logE("Error", t.message, t)
                }
                override fun onResponse(
                    call: Call<LoginModel?>,
                    response: Response<LoginModel?>
                ) {
                    logD("response", response.body().toString())
                }
            })*/
        }

        ClickUtil.setOnClickListener(viewBinding.tvJump, viewBinding.ivLogo) {
            when (this) {
                viewBinding.tvJump -> {
                    MainActivity.startActivity(this@LoginActivity)
                    finish()
                }
                viewBinding.ivLogo -> {
                    // 共享元素 转场动画
                    ShareElementActivity.startActivity(
                        this@LoginActivity,
                        CommonUtils.makeSceneTransitionAnimation(this@LoginActivity, viewBinding.ivLogo, "logo")
                    )
                }
            }
        }
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(this, { result ->
            viewBinding.lottieView.visibility = View.GONE
            viewBinding.btnLogin.text = getString(R.string.login)
            if (result.isFailure) {
                ToastUtils.showToast(this, getString(R.string.network_connect_error))
            } else if (result.isSuccess) {
                if (result.toString().contains("ok")) {
                    MainActivity.startActivity(this)
                    ToastUtils.showToast(this, getString(R.string.login_success))
                    // 保存 mmkv
                    saveInfo(viewBinding.userNameET.text.toString(), viewBinding.passwordET.text.toString())
                    finish()
                } else {
                    ToastUtils.showToast(this, getString(R.string.login_failed_check))
                }
            }
        })
    }

    /**
     * mmkv 保存用户名密码
     * @param userName String 用户名
     * @param password String 密码
     */
    private fun saveInfo(userName: String, password: String) {
        mmkv.encode(Constant.USER_NAME, userName)
        mmkv.encode(Constant.PASSWORD, password)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}