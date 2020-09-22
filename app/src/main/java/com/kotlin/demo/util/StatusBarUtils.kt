package com.kotlin.demo.util

import android.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author: zhouchong
 * 创建日期: 2020/9/22 16:13
 * 描述:
 * 修改人:
 * 迭代版本:
 * 迭代说明:
 */
object StatusBarUtils {
    ///////////////////////////////////////////////////////////////////////////
    // status bar
    ///////////////////////////////////////////////////////////////////////////
    private const val TAG_STATUS_BAR = "TAG_STATUS_BAR"
    private const val TAG_OFFSET = "TAG_OFFSET"
    private const val KEY_OFFSET = -123
    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    fun setStatusBarColor(
        @NonNull activity: Activity,
        @ColorInt color: Int,
    ): View? {
        return setStatusBarColor(activity, color, false)
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param isDecor  True to add fake status bar in DecorView,
     * false to add fake status bar in ContentView.
     */
    fun setStatusBarColor(
        @NonNull activity: Activity,
        @ColorInt color: Int,
        isDecor: Boolean,
    ): View? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null
        transparentStatusBar(activity)
        return applyStatusBarColor(activity, color, isDecor)
    }


    /**
     * Set the status bar's color.
     *
     * @param window The window.
     * @param color  The status bar's color.
     */
    fun setStatusBarColor(
        @NonNull window: Window,
        @ColorInt color: Int,
    ): View? {
        return setStatusBarColor(window, color, false)
    }

    /**
     * Set the status bar's color.
     *
     * @param window  The window.
     * @param color   The status bar's color.
     * @param isDecor True to add fake status bar in DecorView,
     * false to add fake status bar in ContentView.
     */
    fun setStatusBarColor(
        @NonNull window: Window,
        @ColorInt color: Int,
        isDecor: Boolean,
    ): View? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null
        transparentStatusBar(window)
        return applyStatusBarColor(window, color, isDecor)
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    fun setStatusBarColor(
        @NonNull fakeStatusBar: View,
        @ColorInt color: Int,
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        val activity: Activity = getActivityByContext(fakeStatusBar.context) ?: return
        transparentStatusBar(activity)
        fakeStatusBar.visibility = View.VISIBLE
        val layoutParams = fakeStatusBar.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = getStatusBarHeight()
        fakeStatusBar.setBackgroundColor(color)
    }

    fun transparentStatusBar(activity: Activity) {
        transparentStatusBar(activity.window)
    }

    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val vis = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = option or vis
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun applyStatusBarColor(
        activity: Activity,
        color: Int,
        isDecor: Boolean,
    ): View? {
        return applyStatusBarColor(activity.window, color, isDecor)
    }

    private fun applyStatusBarColor(
        window: Window,
        color: Int,
        isDecor: Boolean,
    ): View? {
        val parent =
            if (isDecor) window.decorView as ViewGroup else (window.findViewById<View>(R.id.content) as ViewGroup)
        var fakeStatusBarView =
            parent.findViewWithTag<View>(TAG_STATUS_BAR)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            fakeStatusBarView =
                createStatusBarView(window.context, color)
            parent.addView(fakeStatusBarView)
        }
        return fakeStatusBarView
    }

    fun getStatusBarHeight(): Int {
        val resources = Resources.getSystem()
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }


    private fun createStatusBarView(
        context: Context,
        color: Int,
    ): View? {
        val statusBarView = View(context)
        statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight())
        statusBarView.setBackgroundColor(color)
        statusBarView.tag = TAG_STATUS_BAR
        return statusBarView
    }

    fun getActivityByContext(context: Context): Activity? {
        val activity = getActivityByContextInner(context)
        return if (! isActivityAlive(activity)) null else activity
    }
    fun isActivityAlive(activity: Activity?): Boolean {
        return (activity != null && !activity.isFinishing
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed))
    }
    private fun getActivityByContextInner(context: Context): Activity? {
        var context: Context? = context ?: return null
        val list: MutableList<Context> = ArrayList()
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            val activity: Activity =
                getActivityFromDecorContext(context)!!
            if (activity != null) return activity
            list.add(context)
            context = context.baseContext
            if (context == null) {
                return null
            }
            if (list.contains(context)) {
                // loop context
                return null
            }
        }
        return null
    }

    private fun getActivityFromDecorContext(context: Context?): Activity? {
        if (context == null) return null
        if (context.javaClass.name == "com.android.internal.policy.DecorContext") {
            try {
                val mActivityContextField = context.javaClass.getDeclaredField("mActivityContext")
                mActivityContextField.isAccessible = true
                return (mActivityContextField[context] as WeakReference<Activity?>).get()
            } catch (ignore: Exception) {
            }
        }
        return null
    }
}