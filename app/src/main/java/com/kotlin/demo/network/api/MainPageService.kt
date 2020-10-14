package com.kotlin.demo.network.api

import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.model.LoginModel
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.network.ServiceCreator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:56
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
interface MainPageService {

    @GET
    fun getBanner(@Url url: String): Call<BannerModel>

    @GET
    fun getGanHuo(@Url url: String): Call<GanHuoModel>

    @GET
    fun getMeiZi(@Url url: String): Call<MeiZiModel>

    @POST("/service/mobileLogin.action")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<LoginModel>

    companion object {
        /**
         * banner
         */
        const val BANNER_URL = "${ServiceCreator.GANK_URL}/api/v2/banners"

        /**
         * 干货
         */
        const val GANHUO_URL =
            "${ServiceCreator.GANK_URL}/api/v2/data/category/GanHuo/type/Android"

        /**
         * 干货
         */
        const val MEIZI =
            "${ServiceCreator.GANK_URL}/api/v2/data/category/Girl/type/Girl"
    }
}