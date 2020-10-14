package com.kotlin.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.demo.R
import com.kotlin.demo.base.BaseActivity
import com.kotlin.demo.gank.LoginViewModel
import com.kotlin.demo.param.LoginParams
import com.kotlin.demo.ui.activity.main.Main2Activity
import com.kotlin.demo.util.InjectUtil
import com.kotlin.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this, InjectUtil.postLoginFactory()).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        btnLogin.setOnClickListener {

            if (userNameET.text.isNullOrBlank() || passwordET.text.isNullOrBlank()) {
                ToastUtils.showToast(this, getString(R.string.user_name_and_pwd_can_not_be_empty))
                return@setOnClickListener
            }
            lottieView.visibility = View.VISIBLE
            btnLogin.text = ""
            viewModel.onLogin(LoginParams(userNameET.text.toString(), passwordET.text.toString()))

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
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(this, Observer { result ->
            lottieView.visibility = View.GONE
            btnLogin.text = getString(R.string.login)
            if (result.isFailure) {
                ToastUtils.showToast(this, getString(R.string.network_connect_error))
                return@Observer
            } else if (result.isSuccess) {
                if (result.toString().contains("ok")) {
                    Main2Activity.startActivity(this)
                    ToastUtils.showToast(this, getString(R.string.login_success))
                    finish()
                } else {
                    ToastUtils.showToast(this, getString(R.string.login_failed))
                    return@Observer
                }
            }
        })
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}