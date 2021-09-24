package com.swc.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.multidex.MultiDexApplication
import com.swc.common.util.LOG
import com.swc.common.util.PopupDialogUtil
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeoutException


/**
Created by sangwn.choi on2020-06-30

 **/
abstract class BaseApplication : MultiDexApplication() {
    protected var mActivityCount = 0
    abstract val lifecycleCallback: ActivityLifecycleCallbacks

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotiChannels()
        }

        registerActivityLifecycleCallbacks(lifecycleCallback)

        //rx
        RxJavaPlugins.setErrorHandler { e ->
            var error = e
            if (error is UndeliverableException) {
                error = e.cause
            }
            if (error is IOException || error is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }

            if (error is TimeoutException) {
                //API timeout. Let each API call subscription handle the error.
//                PopupDialogUtil.showApiTimeoutPopup(applicationContext, this.)
                return@setErrorHandler
            }

            LOG.e("Undeliverable exception received, not sure what to do$error")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun initNotiChannels() {
    }
}