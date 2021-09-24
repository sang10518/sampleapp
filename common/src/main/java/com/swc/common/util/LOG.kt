package com.swc.common.util

import android.content.Context
import android.util.Log
import com.swc.common.BuildConfig

/**
Created by sangwn.choi on2020-06-29

 **/
//To be improved...
object LOG {
    private const val TAG = "swc"

    @JvmStatic
    fun e(tag: String? = null, msg: String?) {
        if (!BuildConfig.DEBUG) return
        Log.e(tag ?: TAG, msg?.run { this } ?: "null")
    }

    @JvmStatic
    fun e(msg: String?) {
        if (!BuildConfig.DEBUG) return
        Log.e(TAG, msg.toString())
    }

    @JvmStatic
    fun exception(context: Context, e: Exception) {
        if (!BuildConfig.DEBUG) return
        Log.e(TAG, "exception")
        e.printStackTrace()
    }

    @JvmStatic
    fun largeLog(content: String?) {
        if (!BuildConfig.DEBUG) return
        content?.run {
            if (content.length > 4000) {
                e(content.substring(0, 4000))
                largeLog(content.substring(4000))
            } else {
                e(content)
            }
        }?: run {
            e(TAG, content.toString())
        }

    }
}