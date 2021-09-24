package com.swc.sampleapp

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.swc.common.BaseApplication
import com.swc.common.util.LOG


/**
Created by sangwn.choi on2020-06-30

 **/
class UserApplication : BaseApplication() {
    var mIsNotiWorkerRunning = false
    override val lifecycleCallback = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
            mActivityCount++
            mIsNotiWorkerRunning = false
            WorkManager.getInstance(activity).cancelAllWork()
        }

        override fun onActivityDestroyed(p0: Activity) {
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            mActivityCount--
            if (mActivityCount <= 0 && !mIsNotiWorkerRunning) {
                mIsNotiWorkerRunning = true
                LOG.e("send noti worker")
//                NotiWorker.sendPeriodicNoti(activity)
//                NotiWorker.sendOneTimeNoti(activity)

                //앱을 나가기 전, 마지막 시간을 기록해 둔다.
                val sharedPrefs = activity.getSharedPreferences(
                    activity.packageName,
                    Context.MODE_PRIVATE
                )

                val editor = sharedPrefs.edit()
                editor.putLong(KEY_USER_LAST_TIME_ON_APP, System.currentTimeMillis())
                editor.apply()
            }
        }

        override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        }

        override fun onActivityResumed(p0: Activity) {
        }

    }

    override fun onCreate() {
        LOG.e("swc", "UserApplication onCreate")
        super.onCreate()
    }

    companion object {
        const val KEY_USER_LAST_TIME_ON_APP = "keyUser_lastdfimxzonapp"
    }

}