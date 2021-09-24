package com.swc.common.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.swc.common.BuildConfig
import com.swc.common.R
import com.swc.common.ui.fragment.PopupDialogFragment
import com.swc.common.util.AppInstance
import com.swc.common.util.AppInstance.appId
import com.swc.common.util.AppInstance.launcherLog
import com.swc.common.util.AppInstance.userInfo
import com.swc.common.util.LOG
import com.swc.common.util.NetworkUtil.*
import com.swc.common.util.PopupDialogUtil
import com.trello.rxlifecycle4.android.ActivityEvent
import io.reactivex.rxjava3.core.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
Created by sangwn.choi on2020-07-03

 **/
abstract class BaseSplashActivity : BaseActivity() {
    abstract val myClass: Class<*>?
    private var mIsBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Single.just(0).delay(1000, TimeUnit.MILLISECONDS)
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe({
                checkPrerequisites()
            }, {

            })
    }

    private fun checkPrerequisites() {
        PrerequisiteCheckState.NetworkCheckState.proceed(this@BaseSplashActivity)
    }

    sealed class PrerequisiteCheckState() {
        object NetworkCheckState : PrerequisiteCheckState()
        object SecurityCheckState : PrerequisiteCheckState()
        object FinalCheckState : PrerequisiteCheckState()

        fun proceed(activity: BaseSplashActivity) {
            LOG.e("proceed ${this.javaClass.simpleName}")
            when (this) {
                is NetworkCheckState -> {
                    //인터넷 체크->
                    //오프라인일 경우엔 더이상 진행하지 않음.
                    //모바일 -> 팝업 확인 후 동의 시 다음 단계 진행
                    //WIFI -> 다음 단계 진행
                    when (getNetworkStatus(activity)) {
                        NETWORK_NONE -> {
                            LOG.e("NETWORK_NONE")
                            // 네트워크 연결 안됨
                            PopupDialogUtil.showNoNetworkPopup(activity, activity.supportFragmentManager) {
                                activity.finishAndRemoveTaskSafely()
                            }

                        }

                        NETWORK_MOBILE -> {
                            LOG.e("NETWORK_MOBILE")

                            val sharedPrefs = activity.getSharedPreferences(
                                activity.packageName,
                                Context.MODE_PRIVATE
                            )

                            val isDataPopupSeen: Boolean = sharedPrefs.getBoolean(KEY_NOT_AGAIN_USE_NETWORK_POPUP, false)

                            if (!isDataPopupSeen) {
                                //데이터 과금 알림
                                PopupDialogFragment.PopupDialogBuilder()
                                    .setTitle(null)
                                    .setContent(activity.getString(R.string.msg_network_mobile))
                                    .setOkText(activity.getString(R.string.action_agree))
                                    .setCancelText(activity.getString(R.string.action_exit))
                                    .setOkAction {
                                        val editor = sharedPrefs.edit()
                                        editor.putBoolean(KEY_NOT_AGAIN_USE_NETWORK_POPUP, true)
                                        editor.apply()
                                        SecurityCheckState.proceed(activity)
                                    }
                                    .setCancelAction {
                                        activity.finishAndRemoveTaskSafely()
                                    }
                                    .build()
                                    .show(activity.supportFragmentManager, null)
                            } else {
                                //이미 본 경우 바로 다음 단계 진행
                                SecurityCheckState.proceed(activity)
                            }
                        }

                        NETWORK_WIFI -> {
                            LOG.e("NETWORK_WIFI")
                            SecurityCheckState.proceed(activity)
                        }
                    }
                }

                is SecurityCheckState -> {
                    //보안 체크 진행
                    FinalCheckState.proceed(activity)
                }

                is FinalCheckState -> {
                    activity.moveToNextScreen()
                }
            }
        }
    }


    open fun moveToNextScreen() {
        Single.just(0).compose(bindUntilEvent(ActivityEvent.PAUSE))
            .subscribe({
                myClass?.let {
                    startActivity(Intent(this, it))
                    finish()
                }
            }, {

            })
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        private const val KEY_NOT_AGAIN_USE_NETWORK_POPUP =
            "KEY_NOT_AGAIN_USE_NETWORK_POPUP"

    }

}