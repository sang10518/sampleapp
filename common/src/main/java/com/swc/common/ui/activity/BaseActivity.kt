package com.swc.common.ui.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.swc.common.BuildConfig
import com.swc.common.R
import com.swc.common.ui.custom.SToolbar
import com.swc.common.util.LOG
import com.swc.common.util.PopupDialogUtil
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.drawer_toolbar.view.*
import java.util.concurrent.TimeUnit

/**
Created by sangwn.choi on2020-06-29

 **/
abstract class BaseActivity<VB: ViewBinding>: RxAppCompatActivity() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    public val binding: VB
        get() = _binding as VB

    private var mInteractionSubject: PublishSubject<Int>? = null
//    abstract val mLayoutId: Int
    private val className: String = this.javaClass.simpleName

    abstract fun setLayout()

    open fun setToolbar() {
// viewbinding 적용 시, 여기서 새로 툴바 뷰 추가해줄 필요 없음..
//        findViewById<SToolbar>(R.id.toolbar_id)?.let {
//            LOG.e("set toolbar")
//            it.removeAllViews()
//            it.addView(layoutInflater.inflate(R.layout.drawer_toolbar, null))
//        }
    }


//    fun getToolbar(): SToolbar? {
//        LOG.e("get toolbar is null? ${findViewById<SToolbar>(R.id.toolbar_id) == null}")
//        return findViewById<SToolbar>(R.id.toolbar_id)
//    }
    abstract fun getToolbar() : SToolbar?

    override fun onStart() {
        super.onStart()
        LOG.e(className, "onStart")
    }

    override fun onStop() {
        LOG.e(className, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        LOG.e(className, "onDestroy")
        _binding = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        LOG.e(className, "onResume")

        //interaction 타이머 초기화
        mInteractionSubject?.onNext(0)


    }

    override fun onPause() {
        LOG.e(className, "onPause")

        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LOG.e(className, "onCreate")

        super.onCreate(savedInstanceState)
        //캡쳐 방지
        setCaptureBlock()

        //30분간 미사용시 화면 잠금 처리
        setScreenLockTimer()

        //가로모드 미지원
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setStatusBarColor()

        //set view binding
        _binding = bindingInflater.invoke(layoutInflater)

        //set content view here
        setContentView(binding.root)

        //set toolbar here
        setToolbar()

        //set layout
        setLayout()
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
    }

    private fun setCaptureBlock() {
        if (!BuildConfig.DEBUG) {
            //캡처 방지
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
//        LOG.e("onUserInteraction")
        mInteractionSubject?.onNext(0)
    }

    private fun setScreenLockTimer() {
        mInteractionSubject = PublishSubject.create()
        mInteractionSubject?.run {
            compose(bindUntilEvent(ActivityEvent.DESTROY))
            .observeOn(Schedulers.io())
            .debounce(30, TimeUnit.MINUTES)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //screen lock
                PopupDialogUtil.showScreenLockPopup(this@BaseActivity, supportFragmentManager) {
                    exitApp()
                }
            }, {

            })
        }
    }


    fun finishAndRemoveTaskSafely() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finish()
        }
    }

    open fun exitApp() {
        finishAndRemoveTaskSafely()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            finishAffinity()
//        } else {
//            finish()
//        }
//        System.runFinalization()
//        exitProcess(0)
    }

}