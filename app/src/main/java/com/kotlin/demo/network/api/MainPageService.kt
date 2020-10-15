package com.kotlin.demo.network.api

import com.kotlin.demo.model.BannerModel
import com.kotlin.demo.model.GanHuoModel
import com.kotlin.demo.model.LoginModel
import com.kotlin.demo.model.MeiZiModel
import com.kotlin.demo.network.HttpUrl
import com.kotlin.demo.network.ServiceCreator
import retrofit2.Call
import retrofit2.http.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/2 9:56
 * 描述: GET 请求 POST 请求
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
interface MainPageService {

    /**
     * 无参 GET 请求
     */
    @GET("/api/v2/banners")
    fun getBanner(): Call<BannerModel>

    /**
     * 含参 GET 请求
     */
    @GET("/api/v2/data/category/GanHuo/type/Android/page/{pageNum}/count/50")
    fun getGanHuo(@Path("pageNum") pageNum: Int): Call<GanHuoModel>

    /**
     * @Url 方式
     */
    @GET
    fun getMeiZi(@Url url: String): Call<MeiZiModel>

    /**
     * @Header 根据 BaseUrlName 判断使用哪一个 baseUrl
     * @POST 含参请求
     */
    @Headers("BaseUrlName:login")
    @POST("/service/mobileLogin.action")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<LoginModel>

    companion object {
        /**
         * 妹子
         */
        const val MEIZI =
            "${HttpUrl.GANK_URL}/api/v2/data/category/Girl/type/Girl"
    }
}