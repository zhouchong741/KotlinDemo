package com.kotlin.demo.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.demo.model.LoginModel
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.param.LoginParams

class LoginViewModel(private val repository: MainPageRepository) : ViewModel() {

    private var requestParamLiveData = MutableLiveData<LoginParams>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { loginParams ->
        liveData {
            val result = try {
                val login = repository.postLogin(
                    loginParams.email, loginParams.password
                )
                Result.success(login.result)
            } catch (e: Exception) {
                Result.failure<LoginModel>(e)
            }
            emit(result)
        }
    }

    fun onLogin(loginParams: LoginParams) {
        requestParamLiveData.value = loginParams
    }

}
