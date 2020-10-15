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

    // LiveData 的一个最简单实现，它可以接收数据更新并通知观察者
    private var requestParamLiveData = MutableLiveData<String>()

    // Transformations.switchMap 用来添加一个新数据源并相应地删除前一个数据源
    val dataListLiveData = Transformations.switchMap(requestParamLiveData) {
        liveData {
            val result = try {
                val banner = repository.refreshBanner()
                Result.success(banner)
            } catch (e: Exception) {
                Result.failure<BannerModel>(e)
            }
            emit(result)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = ""
    }
}
