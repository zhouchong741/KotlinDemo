package com.kotlin.demo.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.network.api.MainPageService
import java.lang.Exception

class BannerViewModel(private val repository: MainPageRepository) : ViewModel() {
    var dataList = ArrayList<BannerModel.Item>()

    private var requestParamLiveData = MutableLiveData<String>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData {
            val result = try {
                val banner = repository.refreshBanner(url)
                Result.success(banner)
            } catch (e: Exception) {
                Result.failure<BannerModel>(e)
            }
            emit(result)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = MainPageService.BANNER_URL
    }
}
