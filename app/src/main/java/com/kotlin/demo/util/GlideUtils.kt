package com.kotlin.demo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.kotlin.demo.R
import com.kotlin.demo.extension.dp2px
import com.kotlin.demo.util.GlideUtils.load
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Glide加载图片
 *
 */
object GlideUtils {
    /**
     * use: holder.ivMeiZi.load(url, 4f)
     * @param url 图片地址
     * @param round 圆角，单位dp
     */
    fun ImageView.load(
        url: String,
        round: Float = 0f,
        cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    ) {
        if (round == 0f) {
            Glide.with(this.context)
                .load(url)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(this)
        } else {
            val option = RequestOptions.bitmapTransform(
                RoundedCornersTransformation(
                    dp2px(round),
                    0,
                    cornerType
                )
            ).placeholder(
                R.mipmap.ic_default_img
            )
            Glide.with(this.context)
                .load(url)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .apply(option)
                .into(this)
        }
    }

    /**
     * use: load(this, ivDetail, imgUrl!!, 4f, cornerType)
     */
    fun load(
        context: Context,
        view: ImageView,
        url: String,
        round: Float = 0f,
        cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    ) {
        if (round == 0f) {
            Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(view)
        } else {
            val option = RequestOptions.bitmapTransform(
                RoundedCornersTransformation(
                    dp2px(round),
                    0,
                    cornerType
                )
            ).placeholder(R.mipmap.ic_default_img)
            Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .apply(option)
                .into(view)
        }
    }

    /**
     * Glide加载图片，可以定义配置参数。
     *
     * @param url 图片地址
     * @param options 配置参数
     */
    fun ImageView.load(url: String, options: RequestOptions.() -> RequestOptions) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.mipmap.ic_default_img)
            .error(R.mipmap.ic_default_img)
            .apply(RequestOptions().options())
            .into(this)
    }

    /**
     * ivDetail.load(imgUrl!!)
     */
    fun ImageView.load(url: String) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.mipmap.ic_default_img)
            .error(R.mipmap.ic_default_img)
            .into(this)
    }

    /**
     * 带有共享元素转场动画的 圆角图片
     * use: holder.ivMeiZi.loadTrans(url, 4f)
     */
    fun ImageView.loadTrans(
        url: String, round: Float = 0f,
        cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    ) {
        if (round == 0f) {
            Glide.with(this.context)
                .load(url)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(this)
        } else {
            val option = RequestOptions.bitmapTransform(
                RoundedCornersTransformation(
                    dp2px(round),
                    0,
                    cornerType
                )
            )
            Glide.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(option)
                .into(this)
        }

    }

    /**
     * load(this, ivDetail, imgUrl!!)
     */
    fun load(context: Context, view: ImageView, url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.ic_default_img)
            .error(R.mipmap.ic_default_img)
            .into(view)
    }
}
