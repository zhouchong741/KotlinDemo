package com.kotlin.demo.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.network.MainPageRepository
import com.kotlin.demo.network.api.MainPageService
import kotlinx.coroutines.Dispatchers

class GanHuoViewModel(private val repository: MainPageRepository) : ViewModel() {
    var dataList = ArrayList<GanHuoModel.Item>()

    var pageNum: Int = 1

    private var requestParamLiveData = MutableLiveData<String>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData(Dispatchers.IO) {
            val result = try {
                val ganhuo = repository.refreshGanHuo(url)
                Result.success(ganhuo)
            } catch (e: Exception) {
                Result.failure<GanHuoModel>(e)
            }
            emit(result)
        }
    }

    fun onRefresh() {
        pageNum = 1
        requestParamLiveData.value = MainPageService.GANHUO_URL + "/page/" + pageNum + "/count/50"
    }

    fun onLoad() {
        pageNum++
        requestParamLiveData.value = MainPageService.GANHUO_URL + "/page/" + pageNum + "/count/50"
    }
}
