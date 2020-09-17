package com.kotlin.demo.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.network.api.MainPageService

class MeiZiViewModel(private val repository: MainPageRepository) : ViewModel() {
    var dataList = ArrayList<MeiZiModel.Item>()

    var pageNum: Int = 1

    private var requestParamLiveData = MutableLiveData<String>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData {
            val result = try {
                val meizi = repository.refreshMeiZi(url)
                Result.success(meizi)
            } catch (e: Exception) {
                Result.failure<MeiZiModel>(e)
            }
            emit(result)
        }
    }

    fun onRefresh() {
        pageNum = 1
        requestParamLiveData.value = MainPageService.MEIZI + "/page/" + pageNum + "/count/50"
    }

    fun onLoad() {
        pageNum++
        requestParamLiveData.value = MainPageService.MEIZI + "/page/" + pageNum + "/count/50"
    }
}
